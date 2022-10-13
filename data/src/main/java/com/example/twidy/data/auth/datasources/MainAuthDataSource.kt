package com.example.twidy.data.auth.datasources

import com.example.twidy.data.auth.mappers.CountryResponseMapper
import com.example.twidy.data.auth.mappers.LocationResponseMapper
import com.example.twidy.data.auth.mappers.TokenResponseMapper
import com.example.twidy.data.api.AuthAPI
import com.example.twidy.domain.Failure
import com.example.twidy.domain.NetworkFailure
import com.example.twidy.domain.Success
import com.example.twidy.domain.entities.auth.Location
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import com.example.twidy.domain.entities.auth.Country
import com.example.twidy.domain.entities.auth.Token
import com.example.twidy.domain.Result

class MainAuthDataSource @Inject constructor(
    private val authAPI: AuthAPI,
    private val dispatcher: CoroutineDispatcher,
    private val locationResponseMapper: LocationResponseMapper,
    private val countryResponseMapper: CountryResponseMapper,
    private val tokenResponseMapper: TokenResponseMapper
    ): AuthDataSource {
    override suspend fun fetchLocation(): Result<Location> =
        withContext(dispatcher){
            try {
                val locationData = authAPI.getLocation()
                if(locationData.status=="ok") Success(locationResponseMapper.fromLocationResponseToLocation(locationData.result.location),true) else Failure(locationData.message)
            }
            catch (e: Exception){
                NetworkFailure
            }
        }

    override suspend fun fetchCountries(): Result<List<Country>> =
        withContext(dispatcher){
            try {
                val staticData = authAPI.getStatic("country")
                if(staticData.status=="ok") Success(countryResponseMapper.fromCountryResponseToCountry(staticData.result.countries),true) else Failure(staticData.message)
            }
            catch (e: Exception){
                NetworkFailure
            }
        }

    override suspend fun auth(phone: String): Result<String> =
        withContext(dispatcher){
            try {
                val authData = authAPI.auth(phone)
                if(authData.status=="ok")
                    if(authData.result._d.status=="ERROR")
                        Failure(authData.result._d.status_text)
                    else
                        Success(authData.result.i,true)
                else
                    Failure(authData.message)
            }
            catch (e: Exception){
                NetworkFailure
            }
        }

    override suspend fun confirm(phone: String, id: String, code: String): Result<Token> =
        withContext(dispatcher){
            try{
                val confirmData = authAPI.confirm(phone,id, code)
                if(confirmData.status=="ok")
                    Success(tokenResponseMapper.fromResultConfirmDataToToken(confirmData.result),true)
                else
                    Failure(confirmData.message)
            }
            catch(e: Exception){
                NetworkFailure
            }
        }
}