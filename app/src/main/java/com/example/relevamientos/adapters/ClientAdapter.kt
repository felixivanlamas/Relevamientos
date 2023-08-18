package com.example.relevamientos.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.relevamientos.R
import com.example.relevamientos.entities.Client

class ClientAdapter(
    var clients : MutableList<Client>,
    var onClick: (Int) -> Unit

    ) : RecyclerView.Adapter<ClientAdapter.ClientHolder>(){

    class ClientHolder(v : View) : RecyclerView.ViewHolder(v)
    {
        private var view : View
        init {
            this.view = v
        }
        fun setClientAddress( address : String) {
            val txt_address : TextView = view.findViewById( R.id.txt_address)
            txt_address.text = address
        }
        fun setClientSeller( seller : String) {
            val txt_activator : TextView = view.findViewById( R.id.txt_vendedor)
            txt_activator.text = seller
        }

        fun getCard() : CardView{
            return view.findViewById(R.id.card_client)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClientHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.client_item,parent,false)
        return (ClientHolder(view))
    }

    override fun getItemCount(): Int {
        return clients.size
    }

    override fun onBindViewHolder(holder: ClientHolder, position: Int) {
        holder.setClientAddress(clients[position].address)
        holder.setClientSeller(clients[position].seller)
        holder.getCard().setOnClickListener{
            onClick(position)
        }
    }
}