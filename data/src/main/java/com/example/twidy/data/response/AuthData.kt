package com.example.twidy.data.response

data class AuthData(
    val status: String,
    val result: ResultAuthData,
    val code: Int,
    val message: String
)