package com.example.twidy.domain

import com.example.twidy.data.chats.repositories.ChatsRepository
import com.example.twidy.ui.chats.items.ChatItem
import javax.inject.Inject

class ArchiveChatsUseCase @Inject constructor(private val chatsRepository: ChatsRepository) {
    suspend operator fun invoke(token: String, chats: List<ChatItem>) = chatsRepository.archiveChats(token, chats)
}