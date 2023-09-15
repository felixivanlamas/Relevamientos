package com.example.relevamientos.entities

data class Product(
    val id: Int = -1,
    val description: String="",
){
    constructor() : this(-1,"")
}

