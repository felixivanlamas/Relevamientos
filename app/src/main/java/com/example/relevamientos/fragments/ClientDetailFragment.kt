package com.example.relevamientos.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.relevamientos.R
import com.example.relevamientos.entities.Client
import org.w3c.dom.Text

class ClientDetailFragment : Fragment() {

    lateinit var v: View

    lateinit var btn_qui: Button
    lateinit var btn_back:Button

    lateinit var txt_client_address : TextView
    lateinit var txt_seller : TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_client_detail, container, false)
        btn_qui = v.findViewById(R.id.btn_qui)
        btn_back = v.findViewById(R.id.btn_back)
        txt_client_address = v.findViewById(R.id.txt_client_address)
        txt_seller = v.findViewById(R.id.txt_seller)
        return v
    }

    override fun onStart() {
        super.onStart()
        var client = ClientDetailFragmentArgs.fromBundle(requireArguments()).Client

        txt_client_address.text = client.address
        txt_seller.text = client.seller
        btn_back.setOnClickListener {
            findNavController().popBackStack()
        }
        btn_qui.setOnClickListener{
            var action = ClientDetailFragmentDirections.actionClientDetailFragmentToProductsFragment()
            findNavController().navigate(action)
        }
    }

}