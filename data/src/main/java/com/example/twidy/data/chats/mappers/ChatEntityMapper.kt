package com.example.twidy.data.chats.mappers

import com.example.twidy.data.chats.entities.ChatEntity
import com.example.twidy.dom.entities.Chat

class ChatEntityMapper {

    fun fromChatToChatEntity(chats: List<Chat>): List<ChatEntity>{
        val list = arrayListOf<ChatEntity>()
        for(chat in chats)
            list.add(
                ChatEntity(
                    chat.id,
                    chat.avatar,
                    chat.name,
                    chat.lastMessage,
                    chat.timestamp,
                    chat.newMessages,
                    chat.type
                )
            )
        return list
    }

    fun fromChatEntityToChat(chatEntities: List<ChatEntity>): List<Chat>{
        val list = arrayListOf<Chat>()
        for(chatEntity in chatEntities)
            list.add(
                Chat(
                    chatEntity.id,
                    chatEntity.avatar,
                    chatEntity.name,
                    chatEntity.lastMessage,
                    chatEntity.timestamp,
                    chatEntity.newMessages,
                    chatEntity.type
                )
            )
        return list
    }
}