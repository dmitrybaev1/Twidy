package com.example.twidy.entities

data class Chat (
    var id: Int,
    var last_message: LastMessage,
    var peer: Peer,
    var balance_actual: Int
)