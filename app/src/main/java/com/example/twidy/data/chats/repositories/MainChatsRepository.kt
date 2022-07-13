package com.example.twidy.data.chats.repositories

import com.example.twidy.data.api.Failure
import com.example.twidy.data.api.NetworkFailure
import com.example.twidy.data.api.Result
import com.example.twidy.data.api.Success
import com.example.twidy.data.chats.datasources.ChatsLocalDataSource
import com.example.twidy.data.chats.datasources.ChatsRemoteDataSource
import com.example.twidy.data.entities.Chat
import com.example.twidy.mappers.ChatMapper
import com.example.twidy.ui.chats.items.ChatItem
import com.example.twidy.utils.CryptLib
import com.example.twidy.utils.InternetChecker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainChatsRepository @Inject constructor(
    private val chatsLocalDataSource: ChatsLocalDataSource,
    private val chatsRemoteDataSource: ChatsRemoteDataSource,
    private val internetChecker: InternetChecker
    ): ChatsRepository {

    private val cryptLib = CryptLib()

    override suspend fun fetchChats(token: String): Result<List<ChatItem>>{
        return if(internetChecker.isOnline){
            when(val result = chatsRemoteDataSource.fetchChats(token)){
                is Success<List<Chat>> -> {
                    val chats = decrypt(mapChats(result.data))
                    chatsLocalDataSource.saveChats(chats)
                    Success(chats,result.isRemote)
                }
                is Failure -> result
                is NetworkFailure -> result
            }
        } else{
            val chats = chatsLocalDataSource.fetchChats()
            Success(chats, false)
        }
    }

    override suspend fun archiveChats(token: String,chats: List<ChatItem>): Result<String>{
        return if(internetChecker.isOnline) {
            var ids = ""
            for (i in chats.indices) {
                ids += if (chats[i] == chats[chats.size - 1])
                    chats[i].id.toString()
                else
                    chats[i].id.toString() + ","
            }
            val result = chatsRemoteDataSource.archiveChats(token,ids)
            if(result is Success<String>)
                chatsLocalDataSource.deleteChats(chats)
            result
        } else
            NetworkFailure
    }

    override suspend fun archiveChat(token: String, chat: ChatItem): Result<String>{
        return if(internetChecker.isOnline) {
            val result = chatsRemoteDataSource.archiveChat(token,chat.id)
            if(result is Success<String>)
                chatsLocalDataSource.deleteChat(chat)
            result
        } else
            NetworkFailure
    }

    private fun mapChats(items: List<Chat>): List<ChatItem>{
        val chats = arrayListOf<ChatItem>()
        for(i in items)
            chats.add(ChatMapper.chatToChatItem(i))
        return chats
    }
    private suspend fun decrypt(chatsList:List<ChatItem>): List<ChatItem> =
        withContext(Dispatchers.IO) {
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
            newList
        }
}