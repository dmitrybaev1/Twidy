package com.example.twidy.domain

import com.example.twidy.data.chats.repositories.ChatsRepository
import javax.inject.Inject

class GetChatsUseCase @Inject constructor(private val chatsRepository: ChatsRepository) {
    suspend operator fun invoke(token: String) = chatsRepository.fetchChats(token)
}