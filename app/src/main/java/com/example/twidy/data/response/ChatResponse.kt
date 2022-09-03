package com.example.twidy.data.response

data class ChatResponse (
    var id: Int,
    var last_message: LastMessage,
    var peer: Peer,
    var balance_actual: Int
)