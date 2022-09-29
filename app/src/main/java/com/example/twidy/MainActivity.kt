package com.example.twidy

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.twidy.auth.entities.TokenItem
import com.example.twidy.di.MainComponent

class MainActivity : AppCompatActivity() {
    lateinit var mainComponent: MainComponent
    lateinit var toolbar: Toolbar
    //var isInEditModeChats: Boolean = false
    lateinit var navView: BottomNavigationView
    var authData: TokenItem? = null
    lateinit var token: String
    override fun onCreate(savedInstanceState: Bundle?) {
        mainComponent = DaggerMainComponent.factory().create(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navView = findViewById(R.id.nav_view)
        toolbar = findViewById(R.id.toolbar)
        intent.getParcelableExtra<TokenItem>("authData")?.let {
            authData = it
            token = it.access_token
        }?: kotlin.run { token = "65240d7d04b21c60e4e3f6c73174d05e2554a27b" }
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
    }
}
