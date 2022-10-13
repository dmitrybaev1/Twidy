package com.example.twidy.domain.repositories

import com.example.twidy.domain.Result
import com.example.twidy.domain.entities.auth.Country
import com.example.twidy.domain.entities.auth.Location
import com.example.twidy.domain.entities.auth.Token


interface AuthRepository {

    suspend fun fetchLocation(): Result<Location>

    suspend fun fetchCountries(): Result<List<Country>>

    suspend fun auth(phone: String): Result<String>

    suspend fun confirm(phone: String, id: String, code: String): Result<Token>
}