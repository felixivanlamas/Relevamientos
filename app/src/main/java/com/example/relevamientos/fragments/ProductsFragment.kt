package com.example.relevamientos.fragments

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.relevamientos.R
import com.example.relevamientos.activities.UserActivity
import com.example.relevamientos.adapters.ProductAdapter
import com.example.relevamientos.entities.Break
import com.example.relevamientos.repositories.ProductsRepository
import java.net.URLEncoder
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ProductsFragment : Fragment() {

    companion object {
        fun newInstance() = ProductsFragment()
    }

    private lateinit var viewModel: ProductsViewModel

   lateinit var v : View
   lateinit var recyclerProducts : RecyclerView
   lateinit var adapter: ProductAdapter
   lateinit var btn_back_prod:Button
   lateinit var btnEnviarQuiebres : Button
   lateinit var btnCopiarQuiebres : Button
   lateinit var searchProduct : SearchView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_products, container, false)
        recyclerProducts = v.findViewById(R.id.rec_products)
        btn_back_prod = v.findViewById(R.id.btn_back_prod)
        btnEnviarQuiebres = v.findViewById(R.id.btn_env)
        btnCopiarQuiebres = v.findViewById(R.id.btn_env_group)
        searchProduct = v.findViewById(R.id.searchProduct)

        return v
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProductsViewModel::class.java)
    }
    override fun onStart() {
        super.onStart()
        var sellerPhone = ProductsFragmentArgs.fromBundle(requireArguments()).sellerPhone
        var client = ProductsFragmentArgs.fromBundle(requireArguments()).client

        viewModel.filteredProducts.observe(viewLifecycleOwner) { productList ->
            // Configurar el adaptador con la nueva lista de productos filtrada
            if (productList.isNotEmpty()) {
                // Verificar si el adaptador ya está creado
                if (::adapter.isInitialized) {
                    // Si ya está creado, actualizar la lista de productos
                    adapter.updateProducts(productList)
                } else {
                    // Si no está creado, crear uno nuevo y configurarlo
                    adapter = ProductAdapter(productList)
                    recyclerProducts.layoutManager = LinearLayoutManager(context)
                    recyclerProducts.adapter = adapter
                }

                // Notificar cambios en el adaptador
                adapter.notifyDataSetChanged()
            } else {
                // Manejo cuando la lista de productos filtrada está vacía
                Log.d("Error", "La lista de productos filtrada está vacía.")
            }
        }

        searchProduct.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Puedes manejar la acción de enviar si lo necesitas
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Filtra la lista de productos en función del texto del SearchView
                viewModel.filterProducts(newText.orEmpty())
                return true
            }
        })

        btn_back_prod.setOnClickListener{
            findNavController().popBackStack()
        }

        btnEnviarQuiebres.setOnClickListener {
            val selectedProductsIds = adapter.selectedProductIds
            if(selectedProductsIds.isNotEmpty()){
                val activity = requireActivity() as UserActivity
                // Mostrar un cuadro de diálogo de confirmación
                val builder = AlertDialog.Builder(requireContext())
                builder.setTitle("Confirmación")
                builder.setMessage("¿Estás seguro de querer relevar el PDV con los quiebres seleccionados?")
                builder.setPositiveButton("Sí") { _, _ ->
                    val selectedProducts = viewModel.products.value?.filter { it?.id in adapter.selectedProductIds } ?: emptyList()
                    val selectedProductsText = selectedProducts.joinToString("\n") { it.description }
                    // Obtener la fecha actual
                    val calendar = Calendar.getInstance()

                    // Formatear la fecha en el formato "día/mes/año"
                    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    val formattedDate = dateFormat.format(calendar.time)

                    val breakItemList = mutableListOf<Break>()

                    selectedProductsIds.forEach { productId ->
                        val breakItem = Break(client.id, client.seller, client.activator, productId, formattedDate)
                        breakItemList.add(breakItem)
                    }


                    // Agregar el Break al ViewModel
                    viewModel.addBreaks(breakItemList) { success ->
                        if (success) {
                            val message = "Cliente:\n${client.address}\n\nProductos para ejecutar la activación:\n$selectedProductsText"
                            val intent = Intent(Intent.ACTION_VIEW)
                            intent.data = Uri.parse("whatsapp://send?phone=${sellerPhone}&text=${URLEncoder.encode(message, "UTF-8")}")
                            Log.d("Mensaje Wpp Productos", message)
                            try {
                                activity.setSelectedProducts("")
                                startActivity(intent)
                            } catch (e: ActivityNotFoundException) {
                                Log.e("WhatsApp", "WhatsApp no está instalado en el dispositivo.")
                            }
                        } else {
                            // Manejo de errores si la operación con el ViewModel falla
                            // Podrías mostrar un mensaje al usuario o realizar otras acciones según sea necesario
                            Log.e("Add Break", "Error al agregar Break al ViewModel")
                            Toast.makeText(activity, "Error al agregar Break", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                builder.setNegativeButton("No") { _, _ ->
                    // No hacer nada si el usuario elige no confirmar
                }

                val dialog = builder.create()
                dialog.show()
            }
            else{
                Toast.makeText(activity, "No seleccionaste ningún producto", Toast.LENGTH_SHORT).show()
            }
        }

        btnCopiarQuiebres.setOnClickListener {
            val activity = requireActivity() as UserActivity
            val selectedProducts = viewModel.products.value?.filter { it?.id in adapter.selectedProductIds } ?: emptyList()
            val selectedProductsText = selectedProducts.joinToString("\n") { it.description }

            // Obtener la fecha actual
            val calendar = Calendar.getInstance()

            // Formatear la fecha en el formato "día/mes"
            val dateFormat = SimpleDateFormat("dd/MM", Locale.getDefault())
            val formattedDate = dateFormat.format(calendar.time)


            val message = if (!selectedProductsText.isNullOrBlank()) {
                "Codigo de Cliente: ${client.id}\nDireccion: ${client.address}\nActivador: ${activity.getSelectedActivator()}\nDia de visita del activador: $formattedDate\nProductos faltantes: \n$selectedProductsText"
            } else {
                ""
            }

            val clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Mensaje", message)
            clipboard.setPrimaryClip(clip)

            Toast.makeText(activity, "Mensaje copiado al portapapeles", Toast.LENGTH_SHORT).show()
        }
    }


}