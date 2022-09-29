package com.example.twidy.domain.repositories

import com.example.twidy.domain.Result
import com.example.twidy.dat.response.Country
import com.example.twidy.dat.response.Location
import com.example.twidy.dat.response.ResultConfirmData

interface AuthRepository {

    suspend fun fetchLocation(): Result<Location>

    suspend fun fetchCountries(): Result<List<Country>>

    suspend fun auth(phone: String): Result<String>

    suspend fun confirm(phone: String, id: String, code: String): Result<ResultConfirmData>
}