package com.example.twidy.ui.auth.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.twidy.*
import com.example.twidy.api.AuthAPI
import com.example.twidy.entities.*
import com.example.twidy.utils.RetrofitBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.ConnectException
import kotlin.coroutines.CoroutineContext

class AuthViewModel : ViewModel() {
    private var count = 0

    private val _next = MutableLiveData<Int>()
    val next: LiveData<Int> = _next

    private val _error = MutableLiveData<Int>()
    val error: LiveData<Int> = _error

    private val _apiError = MutableLiveData<String>()
    val apiError: LiveData<String> = _apiError

    val phoneNumber = MutableLiveData<String>()
    val phoneCountry = MutableLiveData<Country>()
    val codeNumber =  MutableLiveData<String>()
    val spinnerItemPosition = MutableLiveData<Int>()

    private val _countries = MutableLiveData<StaticData>()
    private lateinit var staticData: StaticData
    val countries: LiveData<StaticData> = _countries

    private val _location = MutableLiveData<LocationData>()
    private lateinit var locationData: LocationData
    val location: LiveData<LocationData> = _location

    private val _auth = MutableLiveData<AuthData>()
    private lateinit var authData: AuthData
    val auth: LiveData<AuthData> = _auth

    private val _confirm = MutableLiveData<ConfirmData>()
    private lateinit var confirmData: ConfirmData
    val confirm: LiveData<ConfirmData> = _confirm

    private lateinit var phone: String
    private lateinit var id: String

    private val retrofit = RetrofitBuilder.build()
    private val job = Job()
    private val context: CoroutineContext
        get() = Dispatchers.Main + job
    private val vmScope = CoroutineScope(context)

    fun next(){
        count++
        _next.value=count
    }
    fun skip(){
        count=5
        _next.value=count
    }
    fun getCountries(){
        vmScope.launch {
            val authApi = retrofit.create(AuthAPI::class.java)
            try {
                staticData = authApi.getStatic("country")
                if(staticData.status == "ok")
                    _countries.postValue(staticData)
                else
                    _apiError.postValue(staticData.message)
            }
            catch (ex: ConnectException){
                _error.postValue(R.string.host_error)
            }
            catch (ex: HttpException){
                _error.postValue(R.string.static_data_error)
            }
        }
    }
    fun getLocation(){
        vmScope.launch {
            val authApi = retrofit.create(AuthAPI::class.java)
            try {
                locationData = authApi.getLocation()
                if(locationData.status == "ok")
                    _location.postValue(locationData)
                else
                    _apiError.postValue(locationData.message)
            }
            catch (ex: ConnectException){
                _error.postValue(R.string.host_error)
            }
            catch (ex: HttpException){
                _error.postValue(R.string.location_error)
            }
        }
    }
    fun auth(){
        vmScope.launch {
            val authApi = retrofit.create(AuthAPI::class.java)
            try{
                phone = phoneCountry.value?.phonecode.toString()+phoneNumber.value
                authData = authApi.auth(phone)
                if(authData.status == "ok") {
                    if(authData.result._d.status=="ERROR")
                        _apiError.postValue(authData.result._d.status_text)
                    else {
                        id = authData.result.i
                        _auth.postValue(authData)
                    }
                }
                else
                    _apiError.postValue(authData.message)
            }
            catch (ex: ConnectException){
                _error.postValue(R.string.host_error)
            }
            catch (ex: HttpException){
                _error.postValue(R.string.auth_error)
            }
        }
    }
    fun confirm(){
        vmScope.launch {
            val authApi = retrofit.create(AuthAPI::class.java)
            try{
                val code = ""+codeNumber.value
                confirmData = authApi.confirm(phone,id,code)
                if(confirmData.status == "ok"){
                    _confirm.postValue(confirmData)
                }
                else
                    _apiError.postValue(confirmData.message)
            }
            catch (ex: ConnectException){
                _error.postValue(R.string.host_error)
            }
            catch (ex: HttpException){
                _error.postValue(R.string.auth_error)
            }
        }
    }
    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}