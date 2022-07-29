package com.example.twidy.data.chats.datasources

import com.example.twidy.data.api.*
import com.example.twidy.data.entities.Chat
import com.example.twidy.data.chats.entities.ChatItem
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
    override suspend fun archiveChats(token: String, chats: List<ChatItem>): Result<String> =
        withContext(dispatcher){
            try{
                if(chats.size==1){
                    chatsAPI.archive(token,chats[0].id)
                    Success("ok",true)
                }
                else {
                    var ids = ""
                    for (i in chats.indices) {
                        ids += if (chats[i] == chats[chats.size - 1])
                            chats[i].id.toString()
                        else
                            chats[i].id.toString() + ","
                    }
                    chatsAPI.archive(token, ids)
                    Success("ok", true)
                }
            }
            catch (e: Exception){
                NetworkFailure
            }
        }

}