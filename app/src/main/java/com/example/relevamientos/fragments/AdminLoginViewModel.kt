package com.example.relevamientos.fragments

import androidx.lifecycle.ViewModel

class AdminLoginViewModel : ViewModel() {
    companion object{
        private const val ADMIN_EMAIL = "f@f.com"
        private const val ADMIN_PASS = "felix123"
    }

    fun loginAdmin(email:String, pass:String): Boolean{
        var response = false
        if (email == ADMIN_EMAIL && pass == ADMIN_PASS){
            response = true
        }
        return response
    }
}