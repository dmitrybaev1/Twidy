package com.example.twidy.data.entities

data class Message (
    var id: Int,
    var user_id: Int,
    var dialog_id: Int,
    var timestamp: Long,
    var type: String,
    var guuid: String,
    var charge_id: String?,
    var cost: Int,
    var is_paid: Boolean,
    var text: String,
    var react: String,
    var user: MessageUser
)