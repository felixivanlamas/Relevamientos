package com.example.relevamientos.fragments
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.relevamientos.R
import com.example.relevamientos.adapters.ClientAdapter
import com.google.android.material.snackbar.Snackbar


class ClientsFragment : Fragment() {

    companion object {
        fun newInstance() = ClientsFragment()
    }

    lateinit var v : View
    private lateinit var viewModel: ClientsViewModel
    lateinit var recyclerClient: RecyclerView
    lateinit var src_client : SearchView
    lateinit var adapter: ClientAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_clients, container, false)
        recyclerClient = v.findViewById(R.id.rec_clients)
        src_client = v.findViewById(R.id.src_client)

        return  v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ClientsViewModel::class.java]

    }

    override fun onStart() {
        super.onStart()

        // Configura el SearchView
        src_client.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Cuando el texto cambia, aplica el filtrado
                viewModel.filterClients(newText ?: "")
                return true
            }
        })

        // Observa los cambios en la lista filtrada
        viewModel.filteredClients.observe(viewLifecycleOwner) { filteredList ->
            adapter.updateData(filteredList)
        }

        adapter = ClientAdapter(viewModel.allClients, viewModel.sellers){position ->
            val client = adapter.clients[position]
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