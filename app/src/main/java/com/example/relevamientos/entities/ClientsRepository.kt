package com.example.relevamientos.entities

class ClientsRepository(){
    var clients : MutableList<Client>
    //var products : MutableList<Product> = ProductsRepository().products

    init {
        clients = mutableListOf()
        clients.add(Client(1,"Madariaga 1250","mm@hotmail.com","Marina","Felix"))
        clients.add(Client(2,"Salta 1030","ss@hotmail.com","Marina","Pablo"))
        clients.add(Client(3,"Eva Peron 3699","oo@hotmail.com","Noe","Felix"))
        clients.add(Client(4,"Vergara 1377","vv@hotmail.com","Matilde","Felix"))
        clients.add(Client(5,"Fonrouge 289","ff@hotmail.com","Matilde","Belen"))
    }
}
