package com.example.relevamientos.fragments

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
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.relevamientos.R
import com.example.relevamientos.activities.UserActivity
import com.example.relevamientos.adapters.ProductAdapter
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
   var repository: ProductsRepository = ProductsRepository()

   lateinit var adapter: ProductAdapter

   lateinit var btn_back_prod:Button

   lateinit var btnEnviarQuiebres : Button

   lateinit var btnCopiarQuiebres : Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_products, container, false)
        recyclerProducts = v.findViewById(R.id.rec_products)
        btn_back_prod = v.findViewById(R.id.btn_back_prod)
        btnEnviarQuiebres = v.findViewById(R.id.btn_env)
        btnCopiarQuiebres = v.findViewById(R.id.btn_env_group)

        return v
    }
    override fun onStart() {
        super.onStart()
        var sellerPhone = ProductsFragmentArgs.fromBundle(requireArguments()).sellerPhone
        var client = ProductsFragmentArgs.fromBundle(requireArguments()).client

        adapter = ProductAdapter(repository.products)
        recyclerProducts.layoutManager = LinearLayoutManager(context)
        recyclerProducts.adapter = adapter
        btn_back_prod.setOnClickListener{
            findNavController().popBackStack()
        }

        btnEnviarQuiebres.setOnClickListener {
            val activity = requireActivity() as UserActivity
            val selectedProducts = repository.products.filter { it.id in adapter.selectedProductIds }
            /*val selectedProductIds = selectedProducts.map { it.id }*/
            val selectedProductsText = selectedProducts.joinToString("\n") { it.description }

            // Construir el mensaje o dejarlo en blanco
            val message = if (!selectedProductsText.isNullOrBlank()) {
                "Cliente:\n${client.address}\n\nProductos para ejecutar la activación:\n$selectedProductsText"
            } else {
                "" // Mensaje en blanco si no hay productos seleccionados
            }
            // Crear una intención para abrir WhatsApp con el mensaje
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("whatsapp://send?phone=${sellerPhone}&text=${URLEncoder.encode(message, "UTF-8")}")

            try {
                activity.setSelectedProducts("")
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                // WhatsApp no está instalado en el dispositivo
                // Puedes manejar esto según tus necesidades
                Log.e("WhatsApp", "WhatsApp no está instalado en el dispositivo.")
            }
        }

        btnCopiarQuiebres.setOnClickListener {
            val activity = requireActivity() as UserActivity
            val selectedProducts = repository.products.filter { it.id in adapter.selectedProductIds }
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