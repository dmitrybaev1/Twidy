package com.example.twidy.data.chats.datasources

import com.example.twidy.data.database.AppDatabase
import com.example.twidy.data.chats.entities.ChatItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainChatsLocalDataSource @Inject constructor(
    private val database: AppDatabase,
    private val dispatcher: CoroutineDispatcher
    ): ChatsLocalDataSource {
    override suspend fun fetchChats(): List<ChatItem> =
        withContext(dispatcher){
            database.chatItemDao().getAll()
        }
    override suspend fun saveChats(chats: List<ChatItem>) =
        withContext(dispatcher){
            database.chatItemDao().insertAll(chats)
        }
    override suspend fun deleteChats(chats: List<ChatItem>) =
        withContext(dispatcher){
            for(chat in chats)
                database.chatItemDao().delete(chat)
        }
}