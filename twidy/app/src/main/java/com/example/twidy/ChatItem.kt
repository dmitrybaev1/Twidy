package com.example.twidy

data class ChatItem (
    var avatar: String,
    var name: String,
    var lastMessage: String,
    var newMessages: Int,
    var checked: Boolean,
    var inCheckedMode: Boolean,
    var type: String
)