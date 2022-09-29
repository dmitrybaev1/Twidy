package com.example.twidy.data.chats.mappers

import com.example.twidy.data.response.ChatResponse
import com.example.twidy.dom.entities.Chat

class ChatResponseMapper {
    fun fromChatResponseToChat(chatResponses: List<ChatResponse>): List<Chat>{
        val list = arrayListOf<Chat>()
        for(chatResponse in chatResponses)
            list.add(
                Chat(
                    chatResponse.id,
                    chatResponse.peer.image.toString(),
                    chatResponse.peer.name,
                    chatResponse.last_message.message,
                    chatResponse.last_message.timestamp,
                    0,
                    chatResponse.peer.type.toString()
                )
            )
        return list
    }
}