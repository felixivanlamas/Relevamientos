package com.example.relevamientos.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Seller(
    val id : String="",
    val name : String="",
    val phoneNumber : String=""
) : Parcelable {
    constructor() : this("","","")
}
