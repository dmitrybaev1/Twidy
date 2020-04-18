package com.example.twidy.api

import com.example.twidy.entities.AuthData
import com.example.twidy.entities.ConfirmData
import com.example.twidy.entities.LocationData
import com.example.twidy.entities.StaticData
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthAPI {
    @GET("database.get")
    suspend fun getStatic(@Query("fields")fields: String): StaticData
    @GET("utils.getLocation")
    suspend fun getLocation(): LocationData
    @GET("auth")
    suspend fun auth(@Query("phone")phone: String): AuthData
    @POST("auth.confirm")
    suspend fun confirm(@Query("phone")phone: String,@Query("i")id: String,@Query("c")code: String): ConfirmData
}