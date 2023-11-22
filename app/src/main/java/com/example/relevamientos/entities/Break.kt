package com.example.relevamientos.entities

import android.location.Address
import com.google.firebase.Timestamp

data class Break (
    val clientId: String = "",
    val sellerId: String = "",
    val activatorId: String = "",
    val productId: String = "",
    val date: String = "",
    var id: String=""
){
    constructor(): this ("","","","","","")
}