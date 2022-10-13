package com.example.twidy.data.chats.datasources

import com.example.twidy.domain.entities.Chat


interface ChatsLocalDataSource {

    suspend fun fetchChats(): List<Chat>

    suspend fun saveChats(chats: List<Chat>)

    suspend fun deleteChats(chats: List<Chat>)

}