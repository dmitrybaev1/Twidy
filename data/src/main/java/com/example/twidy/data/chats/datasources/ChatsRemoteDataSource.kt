package com.example.twidy.data.chats.datasources

import com.example.twidy.domain.entities.Chat
import com.example.twidy.domain.Result

interface ChatsRemoteDataSource {

    suspend fun fetchChats(token: String): Result<List<Chat>>

    suspend fun archiveChats(token: String, chats: List<Chat>): Result<String>

}