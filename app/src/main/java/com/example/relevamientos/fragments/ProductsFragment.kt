package com.example.relevamientos.fragments

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.relevamientos.R
import com.example.relevamientos.activities.UserActivity
import com.example.relevamientos.adapters.ProductAdapter
import com.example.relevamientos.repositories.ProductsRepository
import java.net.URLEncoder

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


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_products, container, false)
        recyclerProducts = v.findViewById(R.id.rec_products)
        btn_back_prod = v.findViewById(R.id.btn_back_prod)
        btnEnviarQuiebres = v.findViewById(R.id.btn_env)

        return v
    }
    override fun onStart() {
        super.onStart()
        var sellerPhone = ProductsFragmentArgs.fromBundle(requireArguments()).sellerPhone
        var clientAddress = ProductsFragmentArgs.fromBundle(requireArguments()).clientAddress

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
                "Cliente:\n${clientAddress}\n\nProductos para ejecutar la activación:\n$selectedProductsText"
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
            findNavController().navigateUp()
        }
    }


}