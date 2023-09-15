package com.example.relevamientos.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.relevamientos.R
import com.google.android.material.snackbar.Snackbar

class AdminLoginFragment : Fragment() {

    companion object {
        fun newInstance() = AdminLoginFragment()
    }
    lateinit var v: View
    private lateinit var viewModel: AdminLoginViewModel
    lateinit var input_email_adm : TextView
    lateinit var input_pass_adm: TextView
    lateinit var btn_login_adm: Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_admin_login, container, false)
        input_email_adm = v.findViewById(R.id.input_email_adm)
        input_pass_adm = v.findViewById(R.id.input_pass_adm)
        btn_login_adm = v.findViewById(R.id.btn_login_adm)

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[AdminLoginViewModel::class.java]
    }

    override fun onStart() {
        super.onStart()
        btn_login_adm.setOnClickListener{
            if (viewModel.loginAdmin(input_email_adm.text.toString(),input_pass_adm.text.toString())){
                val action = AdminLoginFragmentDirections.actionAdminLoginFragmentToAdminActivity()
                findNavController().navigate(action)
            }
            else{
                Snackbar.make(v, "CREDENCIALES INVALIDAS",
                    Snackbar.LENGTH_LONG)
            }

        }
    }

}