package com.example.twidy

import android.net.Credentials
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
    private val _phoneNumber = MutableLiveData<String>()
    val next: LiveData<Int> = _next
    val phoneNumber: LiveData<String> = _phoneNumber
    private val _countries = MutableLiveData<StaticData>()
    private lateinit var staticData: StaticData
    val countries: LiveData<StaticData> = _countries
    private val _location = MutableLiveData<LocationData>()
    private lateinit var locationData: LocationData
    val location: LiveData<LocationData> = _location
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
                    _error.postValue("Ошибка получения статичных данных")
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
                    _error.postValue("Ошибка получения местоположения")
            }
            catch (ex: ConnectException){
                _error.postValue("Ошибка подключения к хосту")
            }
            catch (ex: HttpException){
                _error.postValue("Ошибка получения местоположения")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}