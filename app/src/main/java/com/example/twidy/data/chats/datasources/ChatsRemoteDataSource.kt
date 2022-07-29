package com.example.twidy.data.chats.datasources

import com.example.twidy.data.api.Result
import com.example.twidy.data.entities.Chat
import com.example.twidy.data.chats.entities.ChatItem

interface ChatsRemoteDataSource {

    suspend fun fetchChats(token: String): Result<List<Chat>>

    suspend fun archiveChats(token: String, chats: List<ChatItem>): Result<String>

}