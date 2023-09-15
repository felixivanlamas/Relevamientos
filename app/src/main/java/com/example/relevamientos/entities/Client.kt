package com.example.relevamientos.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Client(
    val address: String="",
    val seller: String="",
    val activator: String="",
    val id: String="",
) : Parcelable {
    constructor() : this("", "", "","")
}
