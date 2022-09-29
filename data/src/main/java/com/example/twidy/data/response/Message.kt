package com.example.twidy.data.response

data class Message (
    val id: Int,
    val user_id: Int,
    val dialog_id: Int,
    val timestamp: Long,
    val type: String,
    val guuid: String,
    val charge_id: String?,
    val cost: Int,
    val is_paid: Boolean,
    val text: String,
    val react: String,
    val user: MessageUser
)