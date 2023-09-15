package com.example.relevamientos.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.relevamientos.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class UserActivity : AppCompatActivity(){

    lateinit var navHostFragment : NavHostFragment
    lateinit var bottomNavView : BottomNavigationView
    private var selectedProducts: String = ""

    fun setSelectedProducts(productsText: String) {
        selectedProducts = productsText
    }

    fun getSelectedProducts(): String {
        return selectedProducts
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        bottomNavView = findViewById(R.id.bottom_nav)
        NavigationUI.setupWithNavController(bottomNavView,navHostFragment.navController)

    }
}