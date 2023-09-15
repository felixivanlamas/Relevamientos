package com.example.relevamientos.repositories

import com.example.relevamientos.entities.Client
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class ClientRepository{
    private val db = Firebase.firestore
    private val collection = db.collection("clients")
    suspend fun getAllClients(): MutableList<Client>{
       return collection.get().await().toObjects(Client::class.java)
    }
}
