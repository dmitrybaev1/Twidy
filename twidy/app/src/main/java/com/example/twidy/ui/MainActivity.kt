package com.example.twidy.ui

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.twidy.R

class MainActivity : AppCompatActivity() {
    lateinit var toolbar: Toolbar
    //var isInEditModeChats: Boolean = false
    lateinit var navView: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navView = findViewById(R.id.nav_view)
        toolbar = findViewById(R.id.toolbar)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_recommendations,
            R.id.navigation_chats,
            R.id.navigation_calls,
            R.id.navigation_settings
        ))
        navView.setupWithNavController(navController)
        setSupportActionBar(toolbar)
        toolbar.setupWithNavController(navController,appBarConfiguration)
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            //менять что-нибудь если был переход на другой фрагмент

        }
    }

}
