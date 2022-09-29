package com.example.twidy.data.response

data class ChatsData(
    val status: String,
    val result: ResultChatsData,
    val code: Int,
    val message: String
)