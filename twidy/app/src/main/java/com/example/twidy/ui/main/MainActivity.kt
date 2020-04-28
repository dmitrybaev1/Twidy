package com.example.twidy.ui.main

import android.content.Intent
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.twidy.R
import com.example.twidy.services.ChatsUpdateService
import android.util.Log
import com.example.twidy.entities.ResultAuthData
import com.example.twidy.entities.ResultConfirmData
import com.example.twidy.ui.chats.vm.ChatsViewModel

class MainActivity : AppCompatActivity() {
    lateinit var toolbar: Toolbar
    //var isInEditModeChats: Boolean = false
    lateinit var navView: BottomNavigationView
    private lateinit var chatsUpdateService: Intent
    var chatsViewModel: ChatsViewModel? = null
    var authData: ResultConfirmData? = null
    lateinit var token: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navView = findViewById(R.id.nav_view)
        toolbar = findViewById(R.id.toolbar)
        intent.getParcelableExtra<ResultConfirmData>("authData")?.let {
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
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            //менять что-нибудь если был переход на другой фрагмент

        }
        chatsUpdateService = Intent(this,ChatsUpdateService::class.java)
        val pi = createPendingResult(1,chatsUpdateService,0)
        chatsUpdateService.putExtra("pi",pi)
        //chatsUpdateService.putExtra("token","65240d7d04b21c60e4e3f6c73174d05e2554a27b")
        chatsUpdateService.putExtra("token",token)
        startService(chatsUpdateService)
    }

    override fun onDestroy() {
        super.onDestroy()
        stopService(chatsUpdateService)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("log","on activity result")
        //обновить UI в chats fragment
        chatsViewModel?.let {
            it.getChats()
        }
    }
}
