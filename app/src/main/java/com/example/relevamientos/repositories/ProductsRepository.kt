package com.example.relevamientos.repositories

import com.example.relevamientos.entities.Product

class ProductsRepository {
    var products : MutableList<Product> = mutableListOf()

    init {
        products.add(Product(1,"Tresemme"))
        products.add(Product(2,"Dove x 180"))
        products.add(Product(3,"Ala SOU + Bot"))
        products.add(Product(4,"Ala SOU doypack"))
        products.add(Product(5,"Skip SOU + Bot"))
        products.add(Product(6,"Skip SOU doypack"))
        products.add(Product(7,"Hellmanns Clasica x 250"))
        products.add(Product(7,"Hellmanns Clasica x 500"))
        products.add(Product(8,"Hellmanns Ketchup x 250"))
        products.add(Product(9,"Savora x 250"))
        products.add(Product(10,"Dove Botella x 200"))
    }
}