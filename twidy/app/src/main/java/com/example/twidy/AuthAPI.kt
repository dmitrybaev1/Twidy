package com.example.twidy

import retrofit2.http.GET
import retrofit2.http.Query

interface AuthAPI {
    @GET("database.get")
    suspend fun getStatic(@Query("fields")fields: String): StaticData
    @GET("utils.getLocation")
    suspend fun getLocation(): LocationData
}