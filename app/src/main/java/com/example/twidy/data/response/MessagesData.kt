package com.example.twidy.data.response

data class MessagesData (
    var status: String,
    var result: ResultMessagesData,
    var code: Int,
    var message: String
)
