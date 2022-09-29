package com.example.twidy.data.auth.repositories

import com.example.twidy.data.auth.datasources.AuthDataSource
import com.example.twidy.domain.entities.auth.Location
import javax.inject.Inject
import com.example.twidy.domain.repositories.AuthRepository
import com.example.twidy.domain.entities.auth.Country
import com.example.twidy.domain.entities.auth.Token

class MainAuthRepository @Inject constructor(private val authDataSource: AuthDataSource): AuthRepository {

    override suspend fun fetchLocation(): Result<Location> = authDataSource.fetchLocation()

    override suspend fun fetchCountries(): Result<List<Country>> = authDataSource.fetchCountries()

    override suspend fun auth(phone: String): Result<String> = authDataSource.auth(phone)

    override suspend fun confirm(phone: String, id: String, code: String): Result<Token> = authDataSource.confirm(phone, id, code)
}