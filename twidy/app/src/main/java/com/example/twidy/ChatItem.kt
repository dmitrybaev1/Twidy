package com.example.twidy

data class ChatItem (
    var avatar: String,
    var personName: String,
    var lastMessage: String,
    var cost: Int,
    var checked: Boolean,
    var inCheckedMode: Boolean
)