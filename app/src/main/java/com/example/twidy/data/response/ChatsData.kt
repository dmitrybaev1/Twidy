package com.example.twidy.data.response

data class ChatsData(
    var status: String,
    var result: ResultChatsData,
    var code: Int,
    var message: String
)