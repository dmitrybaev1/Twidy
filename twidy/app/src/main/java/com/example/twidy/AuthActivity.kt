package com.example.twidy

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.twidy.databinding.ActivityAuthBinding
//TODO переделать многое на binding
class AuthActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var authVm: AuthViewModel
    private lateinit var viewFlipper: ViewFlipper
    private lateinit var mainLayout: LinearLayout
    private lateinit var phoneEditText: EditText
    private lateinit var codeEditText: EditText
    private lateinit var getCodeButton: Button
    private lateinit var nextCheckButton: Button
    private lateinit var phoneCodeSpinner: Spinner
    private lateinit var phoneCodeAdapter: PhoneCodeSpinnerAdapter
    private lateinit var countryTextView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authVm = ViewModelProviders.of(this).get(AuthViewModel::class.java)
        val binding: ActivityAuthBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_auth)
        binding.lifecycleOwner = this
        binding.auth = authVm
        viewFlipper = findViewById(R.id.view_flipper)
        mainLayout = findViewById(R.id.main_layout)
        getCodeButton = findViewById(R.id.getcode_button)
        nextCheckButton = findViewById(R.id.nextcheck_button)
        phoneEditText = findViewById(R.id.phone_edittext)
        codeEditText = findViewById(R.id.code_edittext)
        countryTextView = findViewById(R.id.country_textview)
        phoneCodeSpinner = findViewById(R.id.phone_spinner)
        phoneCodeSpinner.onItemSelectedListener = this
        phoneEditText.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(p0!!.length<10)
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
                if(p0!!.length<6)
                    nextCheckButton.visibility=View.INVISIBLE
                else
                    nextCheckButton.visibility=View.VISIBLE
            }

        })
        authVm.getCountries()
        authVm.next.observe(this, Observer {
            //TODO: переходы можно заанимировать
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
        authVm.countries.observe(this, Observer {staticData ->
            //TODO: тут сформировать спиннер со странами
            phoneCodeAdapter = PhoneCodeSpinnerAdapter(this,R.layout.code_spinner_item,staticData.result.country)
            phoneCodeSpinner.adapter = phoneCodeAdapter
            authVm.getLocation()
        })
        authVm.location.observe(this, Observer {locationData ->
            phoneCodeSpinner.setSelection(phoneCodeAdapter.getItemPosition(locationData.result.location))
        })
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        countryTextView.text = phoneCodeAdapter.getItem(p2)?.name
    }
}