package com.example.relevamientos.fragments
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.relevamientos.R
import com.example.relevamientos.activities.UserActivity
import com.example.relevamientos.adapters.ClientAdapter
import com.google.android.material.snackbar.Snackbar
class ClientsFragment : Fragment() {

    companion object {
        fun newInstance() = ClientsFragment()
    }

    private lateinit var v : View
    private lateinit var viewModel: ClientsViewModel
    private lateinit var recyclerClient: RecyclerView
    private lateinit var srcClient : SearchView
    private lateinit var adapter: ClientAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_clients, container, false)
        recyclerClient = v.findViewById(R.id.rec_clients)
        srcClient = v.findViewById(R.id.src_client)

        return  v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ClientsViewModel::class.java]
        val activity = requireActivity() as UserActivity
        val selectedActivator = activity.getSelectedActivator()

        // Configura el ViewModel con el activador seleccionado
        viewModel.setNumberActivator(selectedActivator)

        // Observa los cambios en la lista de clientes filtrada
        viewModel.filteredClients.observe(viewLifecycleOwner) { filteredList ->
            // Crea y configura el adaptador aquí, cuando tengas la lista filtrada
            adapter = ClientAdapter(filteredList, viewModel.sellers) { position ->
                val client = filteredList[position]
                val seller = viewModel.findSeller(client.seller)
                if (seller != null) {
                    val action = ClientsFragmentDirections.actionClientsFragmentToClientDetailFragment(client, seller)
                    findNavController().navigate(action)
                } else {
                    val snackbar = Snackbar.make(requireView(), "Vendedor no encontrado", Snackbar.LENGTH_SHORT)
                    snackbar.show()
                }
            }

            // Configura el RecyclerView con el adaptador
            recyclerClient.layoutManager = LinearLayoutManager(context)
            recyclerClient.adapter = adapter
        }
    }


    override fun onStart() {
        super.onStart()
        // Configura el SearchView
        srcClient.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.filterClients(newText ?: "")
                return true
            }
        })
    }
}