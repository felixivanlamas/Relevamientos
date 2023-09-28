package com.example.relevamientos.fragments

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.relevamientos.entities.Activator
import com.example.relevamientos.repositories.ActivatorRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class ScreenLoginViewModel : ViewModel() {
    private var activatorRepository: ActivatorRepository = ActivatorRepository()

    // Usa MutableLiveData para almacenar la lista de activators
    var allActivators: MutableLiveData<List<Activator>> = MutableLiveData()

    init {
        // Llama a la función para cargar la lista de activators en la inicialización
        loadAllActivators()
    }

    private fun loadAllActivators() {
        viewModelScope.launch {
            // Carga la lista de activators desde el repositorio
            var activatorsDeferred = async { activatorRepository.getAllActivators() }

            // Actualiza el valor de MutableLiveData con la lista cargada
            var activatores = activatorsDeferred.await()
            allActivators.postValue(activatores)
        }
    }
}