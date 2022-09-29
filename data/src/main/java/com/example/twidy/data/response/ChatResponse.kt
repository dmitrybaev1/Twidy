package com.example.twidy.data.response

data class ChatResponse (
    val id: Int,
    val last_message: LastMessage,
    val peer: Peer,
    val balance_actual: Int
)