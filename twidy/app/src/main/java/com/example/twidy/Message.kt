package com.example.twidy

data class Message (
    var id: Int,
    var dialog_id: Int,
    var user_id: Int,
    var message: String,
    var type: String,
    var timestamp: Int,
    var fomatted_time: String
)