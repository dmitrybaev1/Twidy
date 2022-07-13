package com.example.twidy.data.chats.repositories

import com.example.twidy.data.api.Result
import com.example.twidy.data.chats.datasources.AuthDataSource
import com.example.twidy.data.entities.Country
import com.example.twidy.data.entities.Location
import com.example.twidy.data.entities.ResultConfirmData
import javax.inject.Inject

class MainAuthRepository @Inject constructor(private val authDataSource: AuthDataSource): AuthRepository {

    override suspend fun fetchLocation(): Result<Location> = authDataSource.fetchLocation()

    override suspend fun fetchCountries(): Result<List<Country>> = authDataSource.fetchCountries()

    override suspend fun auth(phone: String): Result<String> = authDataSource.auth(phone)

    override suspend fun confirm(phone: String, id: String, code: String): Result<ResultConfirmData> = authDataSource.confirm(phone, id, code)
}