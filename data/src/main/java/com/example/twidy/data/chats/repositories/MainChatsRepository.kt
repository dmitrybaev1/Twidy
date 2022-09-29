package com.example.twidy.data.chats.repositories

import com.example.twidy.dom.NetworkFailure
import com.example.twidy.dom.Success
import com.example.twidy.data.chats.datasources.ChatsLocalDataSource
import com.example.twidy.data.chats.datasources.ChatsRemoteDataSource
import com.example.twidy.dom.entities.Chat
import com.example.twidy.dom.InternetChecker
import com.example.twidy.dom.repositories.ChatsRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MainChatsRepository @Inject constructor(
    private val chatsLocalDataSource: ChatsLocalDataSource,
    private val chatsRemoteDataSource: ChatsRemoteDataSource,
    private val internetChecker: InternetChecker
    ): ChatsRepository {

    private val refreshInterval = 5000L

    override fun fetchChats(token: String): Flow<Result<List<Chat>>> =
        flow{
            while(true) {
                if (internetChecker.isOnline)
                    emit(chatsRemoteDataSource.fetchChats(token))
                else
                    emit(Success(chatsLocalDataSource.fetchChats(),false))
                delay(refreshInterval)
            }
        }

    override suspend fun archiveChats(token: String,chats: List<Chat>): Result<String> {
        return if(internetChecker.isOnline) {
            val result = chatsRemoteDataSource.archiveChats(token,chats)
            if(result is Success<String>)
                chatsLocalDataSource.deleteChats(chats)
            result
        } else
            NetworkFailure
    }

}