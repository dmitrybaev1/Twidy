package com.example.twidy

import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.widget.ViewFlipper
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.transition.Visibility
import com.example.twidy.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {
    private lateinit var authVm: AuthViewModel
    private lateinit var viewFlipper: ViewFlipper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authVm = ViewModelProviders.of(this).get(AuthViewModel::class.java)
        val binding: ActivityAuthBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_auth)
        binding.lifecycleOwner = this
        binding.auth = authVm
        viewFlipper = findViewById(R.id.view_flipper)
        authVm.next.observe(this, Observer {
            if(it<viewFlipper.childCount)
                viewFlipper.showNext()
            else
                viewFlipper.visibility = View.GONE
        })
    }
}