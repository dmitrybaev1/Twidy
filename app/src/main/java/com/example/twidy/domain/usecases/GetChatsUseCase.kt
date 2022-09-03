package com.example.twidy.domain.usecases

import com.example.twidy.data.chats.repositories.ChatsRepository
import javax.inject.Inject

class GetChatsUseCase @Inject constructor(private val chatsRepository: ChatsRepository) {
    operator fun invoke(token: String) = chatsRepository.fetchChats(token)
}