package com.example.relevamientos.entities

class ProductsRepository {
    var products : MutableList<Product>
    init {
        products = mutableListOf()
        products.add(Product(1,"Tresemme","Cuidado para el pelo",true))
        products.add(Product(2,"Dove x 180","Cuidado para el pelo",true))
        products.add(Product(3,"Ala sou + Bot","Detergente para la ropa",true))
        products.add(Product(4,"Ala sou doypack","Detergente para la ropa",true))
        products.add(Product(5,"Skip sou + Bot","Detergente para la ropa",true))
        products.add(Product(6,"Skip sou doypack","Detergente para la ropa",true))
        products.add(Product(7,"Sopa Quick","Sopas",true))
    }
}