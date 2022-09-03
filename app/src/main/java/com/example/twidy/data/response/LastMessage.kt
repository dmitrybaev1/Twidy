package com.example.twidy.data.response

data class LastMessage (
    var id: Int,
    var dialog_id: Int,
    var user_id: Int,
    var message: String,
    var type: String,
    var timestamp: Long,
    var fomatted_time: String
)