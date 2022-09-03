package com.example.twidy.data.chats.datasources

import com.example.twidy.domain.Result
import com.example.twidy.domain.entities.Chat

interface ChatsRemoteDataSource {

    suspend fun fetchChats(token: String): Result<List<Chat>>

    suspend fun archiveChats(token: String, chats: List<Chat>): Result<String>

}