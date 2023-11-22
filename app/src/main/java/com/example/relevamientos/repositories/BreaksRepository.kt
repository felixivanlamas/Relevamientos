package com.example.relevamientos.repositories

import android.content.ContentValues
import android.util.Log
import com.example.relevamientos.entities.Break
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class BreaksRepository {
    private val db = Firebase.firestore

    fun  addBreak( breakItem : Break) {
        Log.d("Break Item repositorio",breakItem.toString())
        val id = db.collection("breaks").document().id
        breakItem.id
        db.collection("breaks").document(id).set(breakItem)
    }
}