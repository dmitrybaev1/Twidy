package com.example.twidy.ui.chats.entities

data class ChatItem (
    val id: Int,
    val avatar: String,
    val name: String,
    val lastMessage: String,
    val timestamp: Long,
    val newMessages: Int,
    var checked: Boolean,
    var inCheckedMode: Boolean,
    val type: String
)