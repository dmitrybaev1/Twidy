package com.example.twidy.domain.usecases

import com.example.twidy.domain.repositories.ChatsRepository
import com.example.twidy.domain.entities.Chat
import javax.inject.Inject

class ArchiveChatsUseCase @Inject constructor(private val chatsRepository: ChatsRepository) {
    suspend operator fun invoke(token: String, chats: List<Chat>) = chatsRepository.archiveChats(token, chats)
}