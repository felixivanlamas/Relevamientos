package com.example.relevamientos.repositories

import com.example.relevamientos.entities.Product
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class ProductsRepository {
    private val db = Firebase.firestore
    private val collection = db.collection("products")
    suspend fun getAllProducts(): MutableList<Product>{
        return collection.get().await().toObjects(Product::class.java)
    }
    /*
    var products : MutableList<Product> = mutableListOf()
    init {
        products.add(Product(90,"Tresemme"))
        products.add(Product(91,"Hellmanns Ajo/Ahumada x 250"))
        products.add(Product(92,"Axe Bizzarrap"))
        products.add(Product(2,"Dove x 180"))
        products.add(Product(11,"Dove Botella x 200"))
        products.add(Product(16,"Sedal x 190"))
        products.add(Product(3,"Ala SOU + Bot"))
        products.add(Product(4,"Ala SOU doypack"))
        products.add(Product(5,"Skip SOU + Bot"))
        products.add(Product(6,"Skip SOU doypack"))
        products.add(Product(12,"Cif lavavajillas x 300"))
        products.add(Product(13,"Cif lavavajillas x 500"))
        products.add(Product(14,"Ala lavavajillas x 300"))
        products.add(Product(7,"Hellmanns Clasica x 250"))
        products.add(Product(8,"Hellmanns Clasica x 500"))
        products.add(Product(9,"Hellmanns Ketchup x 250"))
        products.add(Product(10,"Savora x 250"))
    }*/
}