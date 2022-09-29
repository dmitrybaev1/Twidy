package com.example.twidy.data.response


data class ResultConfirmData(
    val access_token: String,
    val call_id: String,
    val created: Int,
    val expires: Int,
    val localUser: LocalUser
)