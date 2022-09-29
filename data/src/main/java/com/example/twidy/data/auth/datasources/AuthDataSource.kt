package com.example.twidy.data.auth.datasources

import com.example.twidy.domain.entities.auth.Country
import com.example.twidy.domain.entities.auth.Location
import com.example.twidy.domain.entities.auth.Token
import com.example.twidy.domain.Result

interface AuthDataSource {

    suspend fun fetchLocation(): Result<Location>

    suspend fun fetchCountries(): Result<List<Country>>

    suspend fun auth(phone: String): Result<String>

    suspend fun confirm(phone: String,id: String,code: String): Result<Token>
}