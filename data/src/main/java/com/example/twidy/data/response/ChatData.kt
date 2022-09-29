package com.example.twidy.data.response

data class ChatData (
    val status: String,
    val result: ResultChatData,
    val code: Int,
    val message: String
)
