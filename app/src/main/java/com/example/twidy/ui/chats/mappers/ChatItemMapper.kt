package com.example.twidy.ui.chats.mappers

import com.example.twidy.data.chats.entities.ChatEntity
import com.example.twidy.domain.entities.Chat
import com.example.twidy.ui.chats.entities.ChatItem

class ChatItemMapper {

    fun fromChatToChatItem(chats: List<Chat>): List<ChatItem>{
        val list = arrayListOf<ChatItem>()
        for(chat in chats)
            list.add(
                ChatItem(
                    chat.id,
                    chat.avatar,
                    chat.name,
                    chat.lastMessage,
                    chat.timestamp,
                    chat.newMessages,
                    false,
                    false,
                    chat.type
                )
            )
        return list
    }

    fun fromChatItemToChat(chatItems: List<ChatItem>): List<Chat>{
        val list = arrayListOf<Chat>()
        for(chatItem in chatItems)
            list.add(
                Chat(
                    chatItem.id,
                    chatItem.avatar,
                    chatItem.name,
                    chatItem.lastMessage,
                    chatItem.timestamp,
                    chatItem.newMessages,
                    chatItem.type
                )
            )
        return list
    }
}