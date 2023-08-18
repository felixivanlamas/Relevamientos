package com.example.relevamientos.adapters

import android.accounts.AuthenticatorDescription
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.relevamientos.R
import com.example.relevamientos.entities.Product

class ProductAdapter( var products : MutableList<Product>) : RecyclerView.Adapter<ProductAdapter.ProductHolder>(){

    class ProductHolder(v: View) : RecyclerView.ViewHolder(v)
    {
        private var view : View
        init {
            this.view = v
        }
        fun setProductDescription (description: String) {
            val chb_item_product : TextView = view.findViewById(R.id.chb_item_product)
            chb_item_product.text = description
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_item,parent,false)
        return (ProductHolder(view))
    }

    override fun onBindViewHolder(holder: ProductHolder, position: Int) {
        holder.setProductDescription(products[position].description)
    }

    override fun getItemCount(): Int {
        return products.size
    }

}
