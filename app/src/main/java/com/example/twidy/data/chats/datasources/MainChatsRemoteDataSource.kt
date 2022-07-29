package com.example.twidy.data.chats.datasources

import com.example.twidy.data.api.*
import com.example.twidy.data.entities.Chat
import com.example.twidy.data.chats.entities.ChatItem
import com.example.twidy.mappers.ChatMapper
import com.example.twidy.utils.CryptLib
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainChatsRemoteDataSource @Inject constructor(
    private val chatsAPI: ChatsAPI,
    private val dispatcher: CoroutineDispatcher
    ): ChatsRemoteDataSource {

    private val cryptLib = CryptLib()

    override suspend fun fetchChats(token: String): Result<List<ChatItem>> =
        withContext(dispatcher) {
            try {
                val chatsData = chatsAPI.getChats(token)
                if (chatsData.status == "ok")
                    Success(decrypt(mapChats(chatsData.result.items)), true)
                else
                    Failure(chatsData.message)
            } catch (e: Exception) {
                NetworkFailure
            }
        }


    override suspend fun archiveChats(token: String, chats: List<ChatItem>): Result<String> =
        withContext(dispatcher) {
            try {
                if (chats.size == 1) {
                    chatsAPI.archive(token, chats[0].id)
                    Success("ok", true)
                } else {
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
            } catch (e: Exception) {
                NetworkFailure
            }
        }

    private fun mapChats(items: List<Chat>): List<ChatItem> {
        val chats = arrayListOf<ChatItem>()
        for (i in items)
            chats.add(ChatMapper.chatToChatItem(i))
        return chats
    }

    private fun decrypt(chatsList: List<ChatItem>): List<ChatItem> {
        val newList = arrayListOf<ChatItem>()
        for (i in chatsList) {
            val item = i.copy(
                lastMessage = cryptLib.decryptCipherTextWithRandomIV(
                    i.lastMessage,
                    cryptLib.sha1(i.id.toString())
                )
            )
            newList.add(item)
        }
        return newList
    }
}