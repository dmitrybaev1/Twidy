package com.example.twidy.data.auth.datasources

import com.example.twidy.domain.Result
import com.example.twidy.data.response.Country
import com.example.twidy.data.response.Location
import com.example.twidy.data.response.ResultConfirmData

interface AuthDataSource {

    suspend fun fetchLocation(): Result<Location>

    suspend fun fetchCountries(): Result<List<Country>>

    suspend fun auth(phone: String): Result<String>

    suspend fun confirm(phone: String,id: String,code: String): Result<ResultConfirmData>
}