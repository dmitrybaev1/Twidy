package com.example.twidy

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.ConnectException
import kotlin.coroutines.CoroutineContext

class AuthViewModel : ViewModel() {
    private var count = 0;
    private val _next = MutableLiveData<Int>()
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error
    val phoneNumber = MutableLiveData<String>()
    val phoneCountry = MutableLiveData<Country>()
    val codeNumber =  MutableLiveData<String>()
    val spinnerItemPosition = MutableLiveData<Int>()
    val next: LiveData<Int> = _next

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
                    _error.postValue(staticData.message)
            }
            catch (ex: ConnectException){
                _error.postValue("Ошибка подключения к хосту")
            }
            catch (ex: HttpException){
                _error.postValue("Ошибка получения статичных данных")
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
                    _error.postValue(locationData.message)
            }
            catch (ex: ConnectException){
                _error.postValue("Ошибка подключения к хосту")
            }
            catch (ex: HttpException){
                _error.postValue("Ошибка получения местоположения")
            }
        }
    }
    fun auth(){
        vmScope.launch {
            val authApi = retrofit.create(AuthAPI::class.java)
            try{
                phone = phoneCountry.value?.phonecode.toString()+phoneNumber.value
                authData = authApi.auth(phone)
                if(locationData.status == "ok") {
                    id=authData.result.i
                    _auth.postValue(authData)
                }
                else
                    _error.postValue(authData.message)
            }
            catch (ex: ConnectException){
                _error.postValue("Ошибка подключения к хосту")
            }
            catch (ex: HttpException){
                _error.postValue("Ошибка авторизации")
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
                    _error.postValue(confirmData.message)
            }
            catch (ex: ConnectException){
                _error.postValue("Ошибка подключения к хосту")
            }
            catch (ex: HttpException){
                _error.postValue("Ошибка авторизации")
            }
        }
    }
    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}