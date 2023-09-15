package com.example.relevamientos.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Spinner
import androidx.navigation.fragment.findNavController
import com.example.relevamientos.R


class ScreenLoginFragment : Fragment() {

    companion object {
        fun newInstance() = ScreenLoginFragment()
    }

    lateinit var v: View
    private lateinit var viewModel: ScreenLoginViewModel
    lateinit var spinner: Spinner
    lateinit var btn_login: Button
    lateinit var btn_adm:Button


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

    }

    override fun onStart() {
        super.onStart()
        btn_adm.setOnClickListener{
            val action = ScreenLoginFragmentDirections.actionScreenLoginFragmentToAdminLoginFragment()
            findNavController().navigate(action)
        }
        btn_login.setOnClickListener {
            val action = ScreenLoginFragmentDirections.actionScreenLoginFragmentToUserActivity()
            findNavController().navigate(action)
        }
    }
}
