package com.example.relevamientos.fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.relevamientos.entities.Client
import com.example.relevamientos.entities.Seller
import com.example.relevamientos.repositories.ClientRepository
import com.example.relevamientos.repositories.SellerRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch



class ClientsViewModel : ViewModel() {
    private var clientRepository : ClientRepository = ClientRepository()
    private var sellerRepository : SellerRepository = SellerRepository()

    private lateinit var activator : String

    private var allClients: List<Client> = mutableListOf()
    var sellers : MutableList<Seller> = mutableListOf()

    val filteredClients: MutableLiveData<List<Client>> = MutableLiveData()

    fun setNumberActivator(numberActivator : String){
        this.activator = numberActivator
        loadAllData()
    }

    private fun loadAllData() {
        viewModelScope.launch {
            // Use async to parallelize loading of clients and sellers
            val clientsDeferred = async { clientRepository.getClientsByActivator(activator) }
            val sellersDeferred = async { sellerRepository.getAllSellers() }

            // Wait for both deferred tasks to complete
            allClients = clientsDeferred.await()
            sellers = sellersDeferred.await()

            // Notify that data has been loaded
            filteredClients.postValue(allClients)
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
