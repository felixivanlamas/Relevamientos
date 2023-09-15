package com.example.relevamientos.fragments
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.relevamientos.R
import com.example.relevamientos.adapters.ClientAdapter
import com.example.relevamientos.entities.Client
import com.google.android.gms.common.api.Response
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch


class ClientsFragment : Fragment() {

    companion object {
        fun newInstance() = ClientsFragment()
    }

    lateinit var v : View
    private lateinit var viewModel: ClientsViewModel
    lateinit var recyclerClient: RecyclerView

    lateinit var adapter: ClientAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_clients, container, false)
        recyclerClient = v.findViewById(R.id.rec_clients)

        return  v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ClientsViewModel::class.java]

    }

    override fun onStart() {
        super.onStart()
        viewModel.getAllClients()
        viewModel.getAllSellers()
        adapter = ClientAdapter(viewModel.clients, viewModel.sellers){position ->
            val client = viewModel.clients[position]
            val seller = viewModel.findSeller(client.seller)
            if (seller != null) {
                val action = ClientsFragmentDirections.actionClientsFragmentToClientDetailFragment(client, seller)
                findNavController().navigate(action)
            } else {
                val snackbar = Snackbar.make(requireView(), "Vendedor no encontrado", Snackbar.LENGTH_SHORT)
                snackbar.show()
            }
        }
        recyclerClient.layoutManager = LinearLayoutManager(context)
        recyclerClient.adapter = adapter
    }
}