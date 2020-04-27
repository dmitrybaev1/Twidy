package com.example.twidy.api

import com.example.twidy.entities.ChatData
import com.example.twidy.entities.ChatsData
import com.example.twidy.entities.FavoriteData
import com.example.twidy.entities.MessagesData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface MainAPI {
    @GET("chats.getList")
    fun getChats(@Header("Authorization")token: String): Call<ChatsData>
    @GET("chats.getMessages")
    suspend fun getMessages(@Header("Authorization")token: String,@Query("id")id: Int,@Query("offset")offset: Int): MessagesData
    @GET("chats.archive")
    suspend fun archive(@Header("Authorization")token: String,@Query("id")id: Int)
    @GET("chats.archive")
    suspend fun archive(@Header("Authorization")token: String,@Query("ids")ids: String)
    @GET("chats.get")
    suspend fun getChat(@Header("Authorization")token: String,@Query("user_id")userId: Int): ChatData
    @GET("user.getFavorite")
    suspend fun getFavorite(@Header("Authorization")token: String): FavoriteData

}