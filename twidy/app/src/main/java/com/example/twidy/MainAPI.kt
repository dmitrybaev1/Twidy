package com.example.twidy

import retrofit2.http.GET
import retrofit2.http.Header

interface MainAPI {
    @GET("chats.getList")
    suspend fun getChats(@Header("Authorization")token: String): ChatsData
}