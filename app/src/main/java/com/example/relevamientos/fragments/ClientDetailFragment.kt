package com.example.relevamientos.fragments

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.relevamientos.R
import com.example.relevamientos.activities.UserActivity
import com.example.relevamientos.entities.Client
import com.google.android.material.snackbar.Snackbar
import org.w3c.dom.Text
import java.net.URLEncoder

class ClientDetailFragment : Fragment() {

    lateinit var v: View

    lateinit var btn_wpp: Button
    lateinit var btn_qui: Button
    lateinit var btn_back:Button

    lateinit var txt_client_address : TextView
    lateinit var txt_seller : TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_client_detail, container, false)
        btn_wpp = v.findViewById(R.id.btn_wpp)
        btn_qui = v.findViewById(R.id.btn_qui)
        btn_back = v.findViewById(R.id.btn_back)
        txt_client_address = v.findViewById(R.id.txt_client_address)
        txt_seller = v.findViewById(R.id.txt_seller)

        return v
    }

    override fun onStart() {
        super.onStart()
        var client = ClientDetailFragmentArgs.fromBundle(requireArguments()).Client
        var seller = ClientDetailFragmentArgs.fromBundle(requireArguments()).Seller

        txt_client_address.text = client.address
        txt_seller.text = seller.name
        btn_back.setOnClickListener {
            findNavController().popBackStack()
        }
        btn_qui.setOnClickListener{
            var action = ClientDetailFragmentDirections.actionClientDetailFragmentToProductsFragment(seller.phoneNumber,client.address)
            findNavController().navigate(action)
        }

        btn_wpp.setOnClickListener {
            try {
                // Crear una intención para abrir WhatsApp con el mensaje
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse("whatsapp://send?phone=${seller.phoneNumber}&text=${URLEncoder.encode("", "UTF-8")}")

                // Iniciar la actividad de WhatsApp
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Snackbar.make(v, e.toString(), Snackbar.LENGTH_LONG).show()
            }
        }
    }

}