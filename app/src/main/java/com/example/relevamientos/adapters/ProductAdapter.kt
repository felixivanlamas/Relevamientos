package com.example.relevamientos.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.example.relevamientos.R
import com.example.relevamientos.entities.Product

class ProductAdapter(var products: List<Product>) : RecyclerView.Adapter<ProductAdapter.ProductHolder>(){

    class ProductHolder(v: View) : RecyclerView.ViewHolder(v)
    {
        private var view : View
        val chbItemProduct: CheckBox = itemView.findViewById(R.id.chb_item_product)
        init {
            this.view = v
        }
        fun bind ( product: Product) {
            chbItemProduct.text = product.description
        }

    }

    val selectedProductIds = mutableSetOf<String>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_item,parent,false)
        return (ProductHolder(view))
    }

    override fun onBindViewHolder(holder: ProductHolder, position: Int) {
        val product = products[position]
        Log.d("Producto adapter", product.toString())
        holder.bind(product)

        holder.chbItemProduct.isChecked = selectedProductIds.contains(product.id)

        holder.chbItemProduct.setOnClickListener {
            if (selectedProductIds.contains(product.id)) {
                selectedProductIds.remove(product.id)
            } else {
                selectedProductIds.add(product.id)
            }

            notifyDataSetChanged()
        }

        holder.itemView.setOnClickListener {
            if (selectedProductIds.contains(product.id)) {
                selectedProductIds.remove(product.id)
            } else {
                selectedProductIds.add(product.id)
            }

            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return products.size
    }

    fun updateProducts(newProducts: List<Product>) {
        products = newProducts
        notifyDataSetChanged()
    }

}
