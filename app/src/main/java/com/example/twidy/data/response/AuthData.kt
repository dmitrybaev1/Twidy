package com.example.twidy.data.response

data class AuthData(
    var status: String,
    var result: ResultAuthData,
    var code: Int,
    var message: String
)