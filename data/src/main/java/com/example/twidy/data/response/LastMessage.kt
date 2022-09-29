package com.example.twidy.data.response

data class LastMessage (
    val id: Int,
    val dialog_id: Int,
    val user_id: Int,
    val message: String,
    val type: String,
    val timestamp: Long,
    val fomatted_time: String
)