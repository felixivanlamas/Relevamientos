package com.example.relevamientos.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.relevamientos.R
import com.example.relevamientos.adapters.ProductAdapter

class ClientsMapFragment : Fragment() {

    lateinit var v : View



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_clients_map, container, false)

        return v
    }
    override fun onStart() {
        super.onStart()

    }


}