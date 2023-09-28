package com.example.relevamientos.fragments

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.relevamientos.R
import com.example.relevamientos.activities.UserActivity
import com.example.relevamientos.entities.Activator


class ScreenLoginFragment : Fragment() {

    companion object {
        fun newInstance() = ScreenLoginFragment()
    }

    lateinit var v: View
    private lateinit var viewModel: ScreenLoginViewModel
    lateinit var spinner: Spinner
    lateinit var btn_login: Button
    lateinit var btn_adm:Button

    lateinit var selectedActivatorId: String

    private var activators: List<Activator> = emptyList()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_screen_login, container, false)
        btn_login = v.findViewById(R.id.btn_login)
        btn_adm = v.findViewById(R.id.btn_adm)
        spinner = v.findViewById(R.id.spinner_activator)

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ScreenLoginViewModel::class.java]

        // Observa el LiveData y actualiza la vista cuando los datos cambien
        viewModel.allActivators.observe(viewLifecycleOwner, Observer<List<Activator>> { activators ->
            updateUI(activators)
        })


    }
    private fun updateUI(activators: List<Activator>) {
        this.activators = activators

        val activatorNames = activators.map { it.id }.toTypedArray()
        spinner.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, activatorNames)
        (spinner.adapter as ArrayAdapter<*>).setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    }

    override fun onStart() {
        super.onStart()

        btn_adm.setOnClickListener{
            val action = ScreenLoginFragmentDirections.actionScreenLoginFragmentToAdminLoginFragment()
            findNavController().navigate(action)
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // Aquí puedes obtener el ID del activador seleccionado
                val selectedActivator = activators[position]
                selectedActivatorId = selectedActivator.id
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // No se seleccionó nada en el Spinner
            }
        }

        btn_login.setOnClickListener {
            val intent = Intent(activity, UserActivity::class.java)
            intent.putExtra("selectedActivatorId", selectedActivatorId)
            startActivity(intent)
        }
    }
}
