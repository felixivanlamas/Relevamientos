package com.example.relevamientos.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.relevamientos.R
import com.example.relevamientos.adapters.ClientAdapter
import com.example.relevamientos.adapters.ProductAdapter
import com.example.relevamientos.entities.ProductsRepository

class ProductsFragment : Fragment() {


   lateinit var v : View
   lateinit var recyclerProducts : RecyclerView
   var repository: ProductsRepository = ProductsRepository()

   lateinit var adapter: ProductAdapter

   lateinit var btn_back_prod:Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_products, container, false)
        recyclerProducts = v.findViewById(R.id.rec_products)
        btn_back_prod = v.findViewById(R.id.btn_back_prod)

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

    }

}