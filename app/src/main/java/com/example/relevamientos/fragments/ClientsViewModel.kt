package com.example.relevamientos.fragments

import androidx.lifecycle.MutableLiveData
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

    var allClients: List<Client> = mutableListOf()
    var sellers : MutableList<Seller> = mutableListOf()

    val filteredClients: MutableLiveData<List<Client>> = MutableLiveData()

    init {
        getAllClients()
        getAllSellers()
    }

    fun getAllClients(){
        viewModelScope.launch{
            allClients = clientRepository.getAllClients()
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

    fun filterClients(query: String) {
        val filteredList = allClients.filter { client ->
            client.address.contains(query, ignoreCase = true)
        }
        filteredClients.postValue(filteredList)
    }
}