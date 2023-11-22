package com.example.relevamientos.entities

data class Product(
    val id: String = "",
    val description: String="",
    val categoria: String="",
    val habilitado: Boolean= true,
){
    constructor() : this("","","",true)
}

