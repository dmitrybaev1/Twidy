package com.example.twidy.data.api

import com.example.twidy.data.entities.ChatData
import com.example.twidy.data.entities.ChatsData
import com.example.twidy.data.entities.FavoriteData
import com.example.twidy.data.entities.MessagesData
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ChatsAPI {
    @GET("chats.getList")
    suspend fun getChats(@Header("Authorization")token: String): ChatsData
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