package com.example.twidy.data.chats.datasources

import com.example.twidy.data.api.*
import com.example.twidy.data.entities.Country
import com.example.twidy.data.entities.Location
import com.example.twidy.data.entities.ResultConfirmData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainAuthDataSource @Inject constructor(
    private val authAPI: AuthAPI,
    private val dispatcher: CoroutineDispatcher
    ): AuthDataSource {
    override suspend fun fetchLocation(): Result<Location> =
        withContext(dispatcher){
            try {
                val locationData = authAPI.getLocation()
                if(locationData.status=="ok") Success(locationData.result.location,true) else Failure(locationData.message)
            }
            catch (e: Exception){
                NetworkFailure
            }
        }

    override suspend fun fetchCountries(): Result<List<Country>> =
        withContext(dispatcher){
            try {
                val staticData = authAPI.getStatic("country")
                if(staticData.status=="ok") Success(staticData.result.countries,true) else Failure(staticData.message)
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

    override suspend fun confirm(phone: String, id: String, code: String): Result<ResultConfirmData> =
        withContext(dispatcher){
            try{
                val confirmData = authAPI.confirm(phone,id, code)
                if(confirmData.status=="ok")
                    Success(confirmData.result,true)
                else
                    Failure(confirmData.message)
            }
            catch(e: Exception){
                NetworkFailure
            }
        }
}