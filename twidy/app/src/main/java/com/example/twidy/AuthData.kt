package com.example.twidy

data class AuthData(
    var status: String,
    var result: ResultAuthData,
    var code: Int,
    var message: String
)