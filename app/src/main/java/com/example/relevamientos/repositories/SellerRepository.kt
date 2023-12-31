package com.example.relevamientos.repositories

import com.example.relevamientos.entities.Seller
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class SellerRepository {
    private val db = Firebase.firestore
    private val collection = db.collection("sellers")
    suspend fun getAllSellers(): MutableList<Seller>{
        return collection.get().await().toObjects(Seller::class.java)
    }
}