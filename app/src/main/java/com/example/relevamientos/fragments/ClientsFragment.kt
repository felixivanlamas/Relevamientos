package com.example.relevamientos.fragments
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.relevamientos.R
import com.example.relevamientos.adapters.ClientAdapter
import com.example.relevamientos.entities.ClientsRepository


class ClientsFragment : Fragment() {

    lateinit var v : View
    lateinit var recyclerClient: RecyclerView
    var repository: ClientsRepository = ClientsRepository()

    lateinit var adapter: ClientAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_clients, container, false)
        recyclerClient = v.findViewById(R.id.rec_clients)

        return  v
    }

    override fun onStart() {
        super.onStart()
        adapter = ClientAdapter(repository.clients){position ->
            val action = ClientsFragmentDirections.actionClientsFragmentToClientDetailFragment(repository.clients[position])
            findNavController().navigate(action)
        }
        recyclerClient.layoutManager = LinearLayoutManager(context)
        recyclerClient.adapter = adapter
    }
}