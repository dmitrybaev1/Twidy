package com.example.twidy.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.twidy.*
import com.example.twidy.domain.Failure
import com.example.twidy.domain.NetworkFailure
import com.example.twidy.domain.Success
import com.example.twidy.domain.entities.auth.Country
import com.example.twidy.domain.entities.auth.Location
import com.example.twidy.domain.entities.auth.Token
import com.example.twidy.domain.usecases.AuthConfirmUseCase
import com.example.twidy.domain.usecases.AuthUseCase
import com.example.twidy.domain.usecases.GetCountriesUseCase
import com.example.twidy.domain.usecases.GetLocationUseCase
import com.example.twidy.auth.entities.CountryItem
import com.example.twidy.auth.entities.LocationItem
import com.example.twidy.auth.entities.TokenItem
import com.example.twidy.auth.mappers.CountryItemMapper
import com.example.twidy.auth.mappers.LocationItemMapper
import com.example.twidy.auth.mappers.TokenItemMapper
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

    @Inject
    lateinit var locationItemMapper: LocationItemMapper

    @Inject
    lateinit var tokenItemMapper: TokenItemMapper

    @Inject
    lateinit var countryItemMapper: CountryItemMapper

    private var count = 0

    private val _next = MutableLiveData<Int>()
    val next: LiveData<Int> = _next

    private val _error = MutableLiveData<Int>()
    val error: LiveData<Int> = _error

    private val _apiError = MutableLiveData<String>()
    val apiError: LiveData<String> = _apiError

    private lateinit var phoneCountry: CountryItem

    val phoneNumber = MutableLiveData<String>()
    val codeNumber =  MutableLiveData<String>()
    val spinnerItemPosition = MutableLiveData<Int>()

    private val _countries = MutableLiveData<List<CountryItem>>()
    val countries: LiveData<List<CountryItem>> = _countries

    private val _location = MutableLiveData<LocationItem>()
    val location: LiveData<LocationItem> = _location

    //возвращается id запроса на авторизацию
    private val _auth = MutableLiveData<String>()
    private lateinit var authId: String
    val auth: LiveData<String> = _auth

    private val _token = MutableLiveData<TokenItem>()
    val token: LiveData<TokenItem> = _token

    private lateinit var phone: String

    fun setPhoneCountry(c: CountryItem){
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
                is Success<List<Country>> -> _countries.value = countryItemMapper.fromCountryToCountryItem(result.data)
                is Failure -> _apiError.value = result.message
                is NetworkFailure -> _error.value = R.string.static_data_error
            }
        }
    }
    fun getLocation(){
        viewModelScope.launch {
            when(val result = getLocationUseCase()){
                is Success<Location> -> _location.value = locationItemMapper.fromLocationToLocationItem(result.data)
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
                is Success<Token> -> _token.value = tokenItemMapper.fromTokenToTokenItem(result.data)
                is Failure -> _apiError.value = result.message
                is NetworkFailure -> _error.value = R.string.auth_error
            }
        }
    }
}