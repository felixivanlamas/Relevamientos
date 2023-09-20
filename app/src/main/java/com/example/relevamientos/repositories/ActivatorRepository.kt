package com.example.relevamientos.repositories

import com.example.relevamientos.entities.Seller
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class ActivatorRepository {
    private val db = Firebase.firestore
    private val collection = db.collection("activators")
    suspend fun getAllSellers(): MutableList<Seller>{
        return collection.get().await().toObjects(Seller::class.java)
    }
}