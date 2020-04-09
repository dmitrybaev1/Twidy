package com.example.twidy

data class Chat (
    var id: Int,
    var last_message: Message,
    var peer: Peer,
    var balance_actual: Int
)