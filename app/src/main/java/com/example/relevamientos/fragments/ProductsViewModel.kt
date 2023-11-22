package com.example.relevamientos.fragments

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.relevamientos.entities.Break
import com.example.relevamientos.entities.Product
import com.example.relevamientos.repositories.BreaksRepository
import com.example.relevamientos.repositories.ProductsRepository
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ProductsViewModel : ViewModel() {
    private var productsRepository : ProductsRepository = ProductsRepository()

    private val db = Firebase.firestore
    // MutableLiveData para la lista de productos
    private val _products = MutableLiveData<List<Product>>()


    // LiveData expuesto públicamente para ser observado desde el fragmento
    val products: LiveData<List<Product>>
        get() = _products

    private val _filteredProducts = MutableLiveData<List<Product>>()

    val filteredProducts: LiveData<List<Product>>
        get() = _filteredProducts


    init {
        // Llamada inicial para cargar los productos al iniciar el ViewModel
        loadProducts()
    }
    private fun loadProducts(){

        viewModelScope.launch {
            val productsDeferred = async { productsRepository.getAllProducts() }

            // Wait for both deferred tasks to complete
            val productList  = productsDeferred.await()

            // Actualizar el MutableLiveData con la nueva lista de productos
            _products.postValue(productList)

            // Configurar la lista filtrada con la lista original al principio
            _filteredProducts.postValue(productList)
        }
    }

    fun filterProducts(query: String) {
        val filteredList = _products.value?.filter {
            it.description.contains(query, ignoreCase = true)
        } ?: emptyList()

        _filteredProducts.postValue(filteredList)
        Log.d("Filter Puto", "Filtered products: $filteredList")
    }



    fun addBreaks (breakItemList: List<Break>, callback: (Boolean) -> Unit) {
        val db = Firebase.firestore
        val collectionRef = db.collection("breaks")

        // Consulta para obtener documentos que coincidan con clientId y date
        collectionRef
            .whereEqualTo("clientId", breakItemList[0].clientId)
            .whereEqualTo("date", breakItemList[0].date)
            .get()
            .addOnSuccessListener { documents ->
                // Elimina los documentos obtenidos
                for (document in documents) {
                    collectionRef.document(document.id).delete()
                }

               breakItemList.forEach {
                   addBreak(it)
               }
                callback(true)
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Error al guardar el Break en Firestore: $e")
                callback(false) // Indicar fracaso a través del callback
            }
    }


    // Función para agregar un Break con un callback para manejar el resultado
    private fun addBreak(breakItem: Break) {
        // Log para verificar la representación del objeto Break antes de guardarlo en Firestore
        Log.d("Break Item repositorio", breakItem.toString())

        // Generar un ID para el documento en Firestore
        val documentReference = db.collection("breaks").document()
        val id = documentReference.id

        // Asignar el ID generado al objeto Break
        breakItem.id = id // Asegúrate de tener un setter para la propiedad id en la clase Break

        // Guardar el objeto Break en Firestore con el ID generado
        db.collection("breaks").document(id).set(breakItem)
            .addOnSuccessListener {
                Log.d("Firestore", "Break guardado correctamente en Firestore")
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Error al guardar el Break en Firestore: $e")
            }
    }
}