package com.example.twidy.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.twidy.*
import com.example.twidy.domain.Failure
import com.example.twidy.domain.NetworkFailure
import com.example.twidy.domain.Success
import com.example.twidy.data.response.*
import com.example.twidy.domain.usecases.AuthConfirmUseCase
import com.example.twidy.domain.usecases.AuthUseCase
import com.example.twidy.domain.usecases.GetCountriesUseCase
import com.example.twidy.domain.usecases.GetLocationUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthViewModel : ViewModel() {

    @Inject
    lateinit var getLocationUseCase: GetLocationUseCase

    @Inject
    lateinit var getCountriesUseCase: GetCountriesUseCase

    @Inject
    lateinit var authUseCase: AuthUseCase

    @Inject
    lateinit var authConfirmUseCase: AuthConfirmUseCase

    private var count = 0

    private val _next = MutableLiveData<Int>()
    val next: LiveData<Int> = _next

    private val _error = MutableLiveData<Int>()
    val error: LiveData<Int> = _error

    private val _apiError = MutableLiveData<String>()
    val apiError: LiveData<String> = _apiError

    private lateinit var phoneCountry: Country

    val phoneNumber = MutableLiveData<String>()
    val codeNumber =  MutableLiveData<String>()
    val spinnerItemPosition = MutableLiveData<Int>()

    private val _countries = MutableLiveData<List<Country>>()
    val countries: LiveData<List<Country>> = _countries

    private val _location = MutableLiveData<Location>()
    val location: LiveData<Location> = _location

    //возвращается id запроса на авторизацию
    private val _auth = MutableLiveData<String>()
    private lateinit var authId: String
    val auth: LiveData<String> = _auth

    private val _confirm = MutableLiveData<ResultConfirmData>()
    val confirm: LiveData<ResultConfirmData> = _confirm

    private lateinit var phone: String

    fun setPhoneCountry(c: Country){
        phoneCountry = c
    }
    fun next(){
        count++
        _next.value=count
    }
    fun skip(){
        count=5
        _next.value=count
    }
    fun getCountries(){
        viewModelScope.launch {
            when(val result = getCountriesUseCase()){
                is Success<List<Country>> -> _countries.value = result.data
                is Failure -> _apiError.value = result.message
                is NetworkFailure -> _error.value = R.string.static_data_error
            }
        }
    }
    fun getLocation(){
        viewModelScope.launch {
            when(val result = getLocationUseCase()){
                is Success<Location> -> _location.value = result.data
                is Failure -> _apiError.value = result.message
                is NetworkFailure -> _error.value = R.string.location_error
            }
        }
    }
    fun auth(){
        viewModelScope.launch {
            phone = phoneCountry.phonecode.toString()+phoneNumber.value
            when(val result = authUseCase(phone)){
                is Success<String> -> {
                    authId = result.data
                    _auth.value = authId
                }
                is Failure -> _apiError.value = result.message
                is NetworkFailure -> _error.value = R.string.auth_error
            }
        }
    }
    fun confirm(){
        viewModelScope.launch {
            val code = ""+codeNumber.value
            when(val result = authConfirmUseCase(phone,authId,code)){
                is Success<ResultConfirmData> -> _confirm.value = result.data
                is Failure -> _apiError.value = result.message
                is NetworkFailure -> _error.value = R.string.auth_error
            }
        }
    }
}