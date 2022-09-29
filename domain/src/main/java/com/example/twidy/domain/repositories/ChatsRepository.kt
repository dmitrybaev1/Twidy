package com.example.twidy.domain.repositories

import com.example.twidy.domain.Result
import com.example.twidy.domain.entities.Chat
import kotlinx.coroutines.flow.Flow

interface ChatsRepository {

    fun fetchChats(token: String): Flow<Result<List<Chat>>>

    suspend fun archiveChats(token: String, chats: List<Chat>): Result<String>

}