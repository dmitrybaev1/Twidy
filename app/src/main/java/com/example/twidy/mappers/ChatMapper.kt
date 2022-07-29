package com.example.twidy.mappers

import com.example.twidy.data.entities.Chat
import com.example.twidy.data.chats.entities.ChatItem

object ChatMapper {
    fun chatToChatItem(chat: Chat): ChatItem = ChatItem(
        chat.id,
        chat.peer.image.toString(),
        chat.peer.name,
        chat.last_message.message,
        chat.last_message.timestamp,
        0,
        false,
        false,
        chat.peer.type.toString()
    )
}