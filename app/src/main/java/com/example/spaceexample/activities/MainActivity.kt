package com.example.spaceexample.activities

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.spaceexample.R
import com.example.spaceexample.utils.HideActionBarActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : HideActionBarActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bottomNavigationView = findViewById<BottomNavigationView
                >(R.id.bottom_nav)
        val navController = findNavController(R.id.nav_host_fragment)
        bottomNavigationView.setupWithNavController(
            navController
        )
    }
}