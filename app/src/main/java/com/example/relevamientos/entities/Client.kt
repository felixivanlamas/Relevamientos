package com.example.relevamientos.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Client(
    var id: Int,
    var address: String,
    var email: String,
    var seller: String,
    var activator: String,
    //var products: MutableList<Product>,
) : Parcelable
