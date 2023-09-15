package com.example.relevamientos.fragments

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat.getSystemService
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.relevamientos.R
import com.example.relevamientos.activities.UserActivity
import com.example.relevamientos.adapters.ProductAdapter
import com.example.relevamientos.repositories.ProductsRepository
import com.google.android.material.snackbar.Snackbar

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

   lateinit var btn_save : Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_products, container, false)
        recyclerProducts = v.findViewById(R.id.rec_products)
        btn_back_prod = v.findViewById(R.id.btn_back_prod)
        btn_save = v.findViewById(R.id.btn_save)

        return v
    }



    override fun onStart() {
        super.onStart()
        adapter = ProductAdapter(repository.products)
        recyclerProducts.layoutManager = LinearLayoutManager(context)
        recyclerProducts.adapter = adapter
        btn_back_prod.setOnClickListener{
            findNavController().popBackStack()
        }
        btn_save.setOnClickListener{
            val selectedProducts = repository.products.filter { it.id in adapter.selectedProductIds }
            /*val selectedProductIds = selectedProducts.map { it.id }*/
            val selectedProductsText = selectedProducts.joinToString("\n") { it.description }

            Log.d("PRODUCTOS ", selectedProductsText)

            // Obtener la actividad actual
            val activity = requireActivity() as UserActivity

            // Almacenar los datos en la actividad
            activity.setSelectedProducts(selectedProductsText)

            // Utilizar navigateUp para volver atrás
            findNavController().navigateUp()

            /*
                val clipboardManager = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("Selected Products", selectedProductsText)
                clipboardManager.setPrimaryClip(clip)

                val sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putString("selectedProductIds", selectedProductIds.joinToString(","))
                editor.apply()*/

        }
    }


}