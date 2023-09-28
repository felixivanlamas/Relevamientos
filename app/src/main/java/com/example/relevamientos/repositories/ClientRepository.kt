package com.example.relevamientos.repositories

import com.example.relevamientos.entities.Client
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class ClientRepository {
    private val db = Firebase.firestore
    private val collection = db.collection("clients")

    suspend fun getClientsByActivator(activatorValue:String): MutableList<Client> {
        val query = collection.whereEqualTo("activator", activatorValue)
        val querySnapshot = query.get().await()
        val clients = mutableListOf<Client>()

        for (document in querySnapshot.documents) {
            val client = document.toObject(Client::class.java)
            client?.let { clients.add(it) }
        }

        return clients
    }
}
