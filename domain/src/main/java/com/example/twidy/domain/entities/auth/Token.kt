package com.example.twidy.domain.entities.auth

data class Token(
    val access_token: String,
    val call_id: String,
    val created: Int,
    val expires: Int
    )
