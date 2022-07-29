package com.example.twidy.data.chats.repositories

import com.example.twidy.data.api.Result
import com.example.twidy.data.chats.entities.ChatItem
import kotlinx.coroutines.flow.Flow

interface ChatsRepository {

    fun fetchChats(token: String): Flow<Result<List<ChatItem>>>

    suspend fun archiveChats(token: String, chats: List<ChatItem>): Result<String>

}