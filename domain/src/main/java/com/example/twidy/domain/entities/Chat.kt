package com.example.twidy.domain.entities


data class Chat(
    val id: Int,
    val avatar: String,
    val name: String,
    val lastMessage: String,
    val timestamp: Long,
    val newMessages: Int,
    val type: String
)
