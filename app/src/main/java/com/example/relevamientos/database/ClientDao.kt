package com.example.relevamientos.database

import com.example.relevamientos.entities.Client

interface ClientDao {
    fun getAllClients(): MutableList<Client?>?

    fun getClientById(id: Int): Client?

    fun createClient(client: Client)

    fun upadateClient(client: Client?)

    fun delete(client: Client?)

}