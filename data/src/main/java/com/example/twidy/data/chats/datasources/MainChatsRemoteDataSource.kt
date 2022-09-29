package com.example.twidy.data.chats.datasources

import com.example.twidy.dat.api.*
import com.example.twidy.data.chats.mappers.ChatResponseMapper
import com.example.twidy.dom.Failure
import com.example.twidy.dom.NetworkFailure
import com.example.twidy.dom.Success
import com.example.twidy.dom.entities.Chat
import com.example.twidy.data.CryptLib
import com.example.twidy.data.api.ChatsAPI
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainChatsRemoteDataSource @Inject constructor(
    private val chatsAPI: ChatsAPI,
    private val dispatcher: CoroutineDispatcher,
    private val chatResponseMapper: ChatResponseMapper
    ): ChatsRemoteDataSource {

    private val cryptLib = CryptLib()

    override suspend fun fetchChats(token: String): Result<List<Chat>> =
        withContext(dispatcher) {
            try {
                val chatsData = chatsAPI.getChats(token)
                if (chatsData.status == "ok")
                    Success(decrypt(chatResponseMapper.fromChatResponseToChat(chatsData.result.items)), true)
                else
                    Failure(chatsData.message)
            } catch (e: Exception) {
                NetworkFailure
            }
        }


    override suspend fun archiveChats(token: String, chats: List<Chat>): Result<String> =
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


    private fun decrypt(chatsList: List<Chat>): List<Chat> {
        val newList = arrayListOf<Chat>()
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