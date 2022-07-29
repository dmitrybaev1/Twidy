package com.example.twidy.data.auth.repositories

import com.example.twidy.data.api.Result
import com.example.twidy.data.entities.Country
import com.example.twidy.data.entities.Location
import com.example.twidy.data.entities.ResultConfirmData

interface AuthRepository {

    suspend fun fetchLocation(): Result<Location>

    suspend fun fetchCountries(): Result<List<Country>>

    suspend fun auth(phone: String): Result<String>

    suspend fun confirm(phone: String, id: String, code: String): Result<ResultConfirmData>
}