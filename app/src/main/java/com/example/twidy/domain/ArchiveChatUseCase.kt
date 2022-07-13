package com.example.twidy.domain

import com.example.twidy.data.chats.repositories.ChatsRepository
import com.example.twidy.ui.chats.items.ChatItem
import javax.inject.Inject

class ArchiveChatUseCase @Inject constructor(private val chatsRepository: ChatsRepository) {
    suspend operator fun invoke(token: String, chat: ChatItem) = chatsRepository.archiveChat(token, chat)
}