package com.example.twidy.data.response

data class ChatData (
    var status: String,
    var result: ResultChatData,
    var code: Int,
    var message: String
)
