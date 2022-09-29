package com.example.twidy.data.response

data class MessagesData (
    val status: String,
    val result: ResultMessagesData,
    val code: Int,
    val message: String
)
