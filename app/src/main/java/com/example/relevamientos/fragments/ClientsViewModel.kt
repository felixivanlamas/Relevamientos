package com.example.relevamientos.fragments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.relevamientos.entities.Client
import com.example.relevamientos.entities.Seller
import com.example.relevamientos.repositories.ClientRepository
import com.example.relevamientos.repositories.SellerRepository
import kotlinx.coroutines.launch


class ClientsViewModel : ViewModel() {
    private var clientRepository : ClientRepository = ClientRepository()
    private var sellerRepository : SellerRepository = SellerRepository()

    var clients : MutableList<Client> = mutableListOf()
    var sellers : MutableList<Seller> = mutableListOf()
    fun getAllClients(){
        viewModelScope.launch{
            clients = clientRepository.getAllClients()
        }
    }
    fun getAllSellers(){
        viewModelScope.launch{
            sellers = sellerRepository.getAllSellers()
        }
    }

    fun findSeller(id: String): Seller? {
        return sellers.find { it.id == id }
    }
}