package com.example.relevamientos.fragments

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.relevamientos.R
import com.example.relevamientos.activities.LoginActivity
import com.example.relevamientos.activities.UserActivity
import com.example.relevamientos.entities.User
import com.google.android.material.snackbar.Snackbar

class ScreenLoginFragment : Fragment() {

    lateinit var v: View
    lateinit var label_email: TextView
    lateinit var input_email: TextView
    lateinit var label_pass: TextView
    lateinit var input_pass: TextView
    lateinit var btn_login: Button

    var input_text_email: String = ""
    var input_text_pass: String = ""

    var user_list: MutableList<User> = mutableListOf()

    private fun findUser(email: String, password: String): User? {
        return user_list.find { user ->
            user.email == email && user.password == password
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_screen_login, container, false)
        label_email = v.findViewById(R.id.label_email)
        input_email = v.findViewById(R.id.input_email)
        label_pass = v.findViewById(R.id.label_pass)
        input_pass = v.findViewById(R.id.input_pass)
        btn_login = v.findViewById(R.id.btn_login)

        user_list.add(User(1, "Felix", "Lamas", "f@f.com", "felix123"))
        user_list.add(User(2, "Alberto", "Lamas", "a@a.com", "alberto123"))
        user_list.add(User(3, "Brenda", "Lamas", "b@b.com", "brenda123"))
        user_list.add(User(4, "Camila", "Lamas", "c@c.com", "camila123"))
        user_list.add(User(5, "Damian", "Lamas", "d@d.com", "damian123"))
        user_list.add(User(6, "Edgardo", "Lamas", "e@e.com", "edgardo123"))
        user_list.add(User(7, "Graciela", "Lamas", "g@g.com", "graciela123"))
        user_list.add(User(8, "Hector", "Lamas", "h@h.com", "hector123"))
        user_list.add(User(9, "Juliana", "Lamas", "j@j.com", "juliana123"))
        user_list.add(User(10, "Karina", "Lamas", "k@k.com", "karina123"))


        return v
    }

    override fun onStart() {
        super.onStart()

        btn_login.setOnClickListener {
            input_text_email = input_email.text.toString()
            input_text_pass = input_pass.text.toString()

            val foundUser = findUser(input_text_email, input_text_pass)

            if (foundUser != null) {
                // Iniciar sesión exitosa
                val intent = Intent(requireContext(), UserActivity::class.java)
                startActivity(intent)
                Snackbar.make(v, "Bienvendido/a ${foundUser.name} ${foundUser.lastName}", Snackbar.LENGTH_SHORT).show()
            } else {
                // Mostrar mensaje de error
                Snackbar.make(v, "Credenciales inválidas", Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}
