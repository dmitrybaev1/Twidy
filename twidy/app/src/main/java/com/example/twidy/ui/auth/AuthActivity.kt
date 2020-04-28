package com.example.twidy.ui.auth

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.twidy.ui.auth.adapters.PhoneCodeSpinnerAdapter
import com.example.twidy.R
import com.example.twidy.databinding.ActivityAuthBinding
import com.example.twidy.ui.auth.vm.AuthViewModel
import com.example.twidy.ui.main.MainActivity
import java.util.*

//TODO переделать многое на binding
class AuthActivity : AppCompatActivity() {
    private lateinit var authVm: AuthViewModel
    private lateinit var viewFlipper: ViewFlipper
    private lateinit var mainLayout: LinearLayout
    private lateinit var firstStepLayout: LinearLayout
    private lateinit var secondStepLayout: LinearLayout
    private lateinit var phoneEditText: EditText
    private lateinit var codeEditText: EditText
    private lateinit var getCodeButton: Button
    private lateinit var nextCheckButton: Button
    private lateinit var phoneCodeSpinner: Spinner
    private lateinit var phoneCodeAdapter: PhoneCodeSpinnerAdapter
    private lateinit var countryTextView: TextView
    private lateinit var backLinkTextView: TextView
    private lateinit var repeatSendCodeTextView: TextView
    private lateinit var hintTypeface: Typeface
    private lateinit var textTypeface: Typeface
    private var seconds = 60
    private var timer: Timer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authVm = ViewModelProviders.of(this).get(AuthViewModel::class.java)
        val binding: ActivityAuthBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_auth
        )
        binding.lifecycleOwner = this
        binding.auth = authVm
        viewFlipper = findViewById(R.id.view_flipper)
        mainLayout = findViewById(R.id.main_layout)
        firstStepLayout = findViewById(R.id.firststep_layout)
        secondStepLayout = findViewById(R.id.secondstep_layout)
        getCodeButton = findViewById(R.id.getcode_button)
        nextCheckButton = findViewById(R.id.nextcheck_button)
        phoneEditText = findViewById(R.id.phone_edittext)
        codeEditText = findViewById(R.id.code_edittext)
        countryTextView = findViewById(R.id.country_textview)
        phoneCodeSpinner = findViewById(R.id.phone_spinner)
        backLinkTextView = findViewById(R.id.back_link_textview)
        repeatSendCodeTextView = findViewById(R.id.repeat_number_textview)
        hintTypeface = Typeface.createFromAsset(assets,"res/font/sfprodisplay_regular.ttf")
        textTypeface = Typeface.createFromAsset(assets,"res/font/sfprodisplay_semibold.ttf")
        phoneEditText.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(p0!!.isNotEmpty()){
                    phoneEditText.typeface = textTypeface
                }
                else{
                    phoneEditText.typeface = hintTypeface
                }

                if(p0.length<10)
                    getCodeButton.visibility=View.INVISIBLE
                else
                    getCodeButton.visibility=View.VISIBLE
            }

        })
        codeEditText.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(p0!!.isNotEmpty()){
                    codeEditText.typeface = textTypeface
                }
                else{
                    codeEditText.typeface = hintTypeface
                }

                if(p0.length<6)
                    nextCheckButton.visibility=View.INVISIBLE
                else
                    nextCheckButton.visibility=View.VISIBLE
            }

        })
        backLinkTextView.setOnClickListener {
            if(seconds==0){
                firstStepLayout.visibility = View.VISIBLE
                secondStepLayout.visibility = View.GONE
            }
        }
        authVm.getCountries()
        authVm.next.observe(this, Observer {
            if(it<viewFlipper.childCount)
                viewFlipper.showNext()
            else {
                viewFlipper.visibility = View.GONE
                mainLayout.visibility = View.VISIBLE
            }
        })
        authVm.error.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })
        authVm.apiError.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })
        authVm.countries.observe(this, Observer {staticData ->
            phoneCodeAdapter =
                PhoneCodeSpinnerAdapter(
                    this,
                    R.layout.code_spinner_item,
                    staticData.result.country
                )
            phoneCodeSpinner.adapter = phoneCodeAdapter
            authVm.getLocation()
        })
        authVm.location.observe(this, Observer {locationData ->
            phoneCodeSpinner.setSelection(phoneCodeAdapter.getItemPosition(locationData.result.location))
        })
        authVm.auth.observe(this, Observer { authData ->
            firstStepLayout.visibility = View.GONE
            secondStepLayout.visibility = View.VISIBLE
            seconds=60
            timer?.let { it.cancel() }
            timer = Timer()
            timer!!.schedule(RepeatNumberTimerTask(),1000,1000)
        })
        authVm.spinnerItemPosition.observe(this, Observer {
            authVm.phoneCountry.value = phoneCodeAdapter.getItem(it)
            countryTextView.text = phoneCodeAdapter.getItem(it)?.name
        })
        authVm.confirm.observe(this, Observer {confirmData ->
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent.putExtra("authData",confirmData.result)
            startActivity(intent)
        })
    }
    inner class RepeatNumberTimerTask : TimerTask(){
        override fun run() {
            if(seconds==60)
                runOnUiThread { repeatSendCodeTextView.visibility = View.VISIBLE}
            if(seconds>0) {
                seconds--
                runOnUiThread { repeatSendCodeTextView.text = getString(R.string.repeat_number, seconds) }
            }
            else if(seconds==0) {
                cancel()
                runOnUiThread {  repeatSendCodeTextView.visibility = View.GONE }
            }
        }

    }
}