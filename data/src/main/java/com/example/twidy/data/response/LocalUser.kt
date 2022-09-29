package com.example.twidy.data.response


data class LocalUser (
    val id: Int,
    val domain: String?,
    val email: String?,
    val balance: Int,
    val last_activity: Int,
    val is_verify: Boolean,
    val is_online: Boolean,
    val is_blocked: Boolean,
    val guuid: String?,
    val phone: String,
    val withdraw: String?
)