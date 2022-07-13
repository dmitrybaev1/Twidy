package com.example.twidy.data.chats.datasources

import com.example.twidy.ui.chats.items.ChatItem

interface ChatsLocalDataSource {

    suspend fun fetchChats(): List<ChatItem>

    suspend fun saveChats(chats: List<ChatItem>)

    suspend fun deleteChats(chats: List<ChatItem>)

    suspend fun deleteChat(chat: ChatItem)
}