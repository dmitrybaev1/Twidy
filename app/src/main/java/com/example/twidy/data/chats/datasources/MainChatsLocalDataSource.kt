package com.example.twidy.data.chats.datasources

import com.example.twidy.data.chats.mappers.ChatEntityMapper
import com.example.twidy.data.database.AppDatabase
import com.example.twidy.domain.entities.Chat
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainChatsLocalDataSource @Inject constructor(
    private val database: AppDatabase,
    private val dispatcher: CoroutineDispatcher,
    private val chatEntityMapper: ChatEntityMapper
    ): ChatsLocalDataSource {
    override suspend fun fetchChats(): List<Chat> =
        withContext(dispatcher){
            chatEntityMapper.fromChatEntityToChat(database.chatDao().getAll())
        }
    override suspend fun saveChats(chats: List<Chat>) =
        withContext(dispatcher){
            database.chatDao().insertAll(chatEntityMapper.fromChatToChatEntity(chats))
        }
    override suspend fun deleteChats(chats: List<Chat>) =
        withContext(dispatcher){
            val list = chatEntityMapper.fromChatToChatEntity(chats)
            for(chatEntity in list)
                database.chatDao().delete(chatEntity)
        }
}