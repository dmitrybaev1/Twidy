package com.example.twidy.data.chats.repositories

import com.example.twidy.data.api.Result
import com.example.twidy.data.chats.entities.ChatItem

interface ChatsRepository {

    suspend fun fetchChats(token: String): Result<List<ChatItem>>

    suspend fun archiveChats(token: String, chats: List<ChatItem>): Result<String>

}