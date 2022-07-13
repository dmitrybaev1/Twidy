package com.example.twidy.data.chats.datasources

import com.example.twidy.data.api.*
import com.example.twidy.data.entities.Chat
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainChatsRemoteDataSource @Inject constructor(
    private val chatsAPI: ChatsAPI,
    private val dispatcher: CoroutineDispatcher
    ): ChatsRemoteDataSource {
    override suspend fun fetchChats(token: String): Result<List<Chat>> =
        withContext(dispatcher) {
            try {
                val chatsData = chatsAPI.getChats(token)
                if (chatsData.status == "ok") Success(chatsData.result.items, true) else Failure(chatsData.message)
            }
            catch (e: Exception){
                NetworkFailure
            }
        }
    override suspend fun archiveChats(token: String, ids: String): Result<String> =
        withContext(dispatcher){
            try{
                chatsAPI.archive(token, ids)
                Success("ok",true)
            }
            catch (e: Exception){
                NetworkFailure
            }
        }

    override suspend fun archiveChat(token: String, id: Int): Result<String> =
        withContext(dispatcher){
            try{
                chatsAPI.archive(token, id)
                Success("ok",true)
            }
            catch (e: Exception){
                NetworkFailure
            }
        }
}