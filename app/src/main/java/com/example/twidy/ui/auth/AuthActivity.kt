package com.example.twidy.ui.auth

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.twidy.ui.auth.adapters.PhoneCodeSpinnerAdapter
import com.example.twidy.R
import com.example.twidy.data.entities.Country
import com.example.twidy.databinding.ActivityAuthBinding
import com.example.twidy.di.AuthComponent
import com.example.twidy.ui.MainActivity
import java.util.*
import kotlin.collections.ArrayList

class AuthActivity : AppCompatActivity() {

    lateinit var authComponent: AuthComponent

    private val authVm: AuthViewModel by viewModels()

    private lateinit var hintTypeface: Typeface
    private lateinit var textTypeface: Typeface
    private lateinit var binding: ActivityAuthBinding
    private lateinit var phoneCodeAdapter: PhoneCodeSpinnerAdapter
    private var seconds = 60
    private var timer: Timer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        authComponent = DaggerAuthComponent.factory().create()
        authComponent.inject(authVm)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this, R.layout.activity_auth
        )
        binding.lifecycleOwner = this
        binding.auth = authVm
        hintTypeface = Typeface.createFromAsset(assets,"res/font/sfprodisplay_regular.ttf")
        textTypeface = Typeface.createFromAsset(assets,"res/font/sfprodisplay_semibold.ttf")
        binding.phoneEdittext.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(p0!!.isNotEmpty()){
                    binding.phoneEdittext.typeface = textTypeface
                }
                else{
                    binding.phoneEdittext.typeface = hintTypeface
                }

                if(p0.length<10)
                    binding.getcodeButton.visibility=View.INVISIBLE
                else
                    binding.getcodeButton.visibility=View.VISIBLE
            }

        })
        binding.codeEdittext.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(p0!!.isNotEmpty()){
                    binding.codeEdittext.typeface = textTypeface
                }
                else{
                    binding.codeEdittext.typeface = hintTypeface
                }

                if(p0.length<6)
                    binding.nextcheckButton.visibility=View.INVISIBLE
                else
                    binding.nextcheckButton.visibility=View.VISIBLE
            }

        })
        binding.backLinkTextview.setOnClickListener {
            if(seconds==0){
                binding.firststepLayout.visibility = View.VISIBLE
                binding.secondstepLayout.visibility = View.GONE
            }
        }
        authVm.getCountries()
        authVm.next.observe(this, Observer {
            if(it<binding.viewFlipper.childCount)
                binding.viewFlipper.showNext()
            else {
                binding.viewFlipper.visibility = View.GONE
                binding.mainLayout.visibility = View.VISIBLE
            }
        })
        authVm.error.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })
        authVm.apiError.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })
        authVm.countries.observe(this, Observer {
            phoneCodeAdapter =
                PhoneCodeSpinnerAdapter(
                    this,
                    R.layout.code_spinner_item,
                    it as ArrayList<Country>
                )
            binding.phoneSpinner.adapter = phoneCodeAdapter
            authVm.getLocation()
        })
        authVm.location.observe(this, Observer {
            binding.phoneSpinner.setSelection(phoneCodeAdapter.getItemPosition(it))
        })
        authVm.auth.observe(this, Observer {
            binding.firststepLayout.visibility = View.GONE
            binding.secondstepLayout.visibility = View.VISIBLE
            seconds=60
            timer?.let { it.cancel() }
            timer = Timer()
            timer!!.schedule(RepeatNumberTimerTask(),1000,1000)
        })
        authVm.spinnerItemPosition.observe(this, Observer {
            authVm.setPhoneCountry(phoneCodeAdapter.getItem(it)!!)
            binding.countryTextview.text = phoneCodeAdapter.getItem(it)!!.name
        })
        authVm.confirm.observe(this, Observer {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent.putExtra("authData",it)
            startActivity(intent)
        })
    }
    inner class RepeatNumberTimerTask : TimerTask(){
        override fun run() {
            if(seconds==60)
                runOnUiThread { binding.repeatNumberTextview.visibility = View.VISIBLE}
            if(seconds>0) {
                seconds--
                runOnUiThread { binding.repeatNumberTextview.text = getString(R.string.repeat_number, seconds) }
            }
            else if(seconds==0) {
                cancel()
                runOnUiThread {  binding.repeatNumberTextview.visibility = View.GONE }
            }
        }

    }
}