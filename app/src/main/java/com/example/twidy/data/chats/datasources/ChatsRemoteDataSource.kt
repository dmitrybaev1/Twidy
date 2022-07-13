package com.example.twidy.data.chats.datasources

import com.example.twidy.data.api.Result
import com.example.twidy.data.entities.Chat

interface ChatsRemoteDataSource {

    suspend fun fetchChats(token: String): Result<List<Chat>>

    suspend fun archiveChats(token: String, ids: String): Result<String>

    suspend fun archiveChat(token: String, id: Int): Result<String>
}