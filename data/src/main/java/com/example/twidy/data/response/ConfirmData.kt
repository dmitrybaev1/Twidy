package com.example.twidy.data.response

data class ConfirmData(
    val status: String,
    val result: ResultConfirmData,
    val code: Int,
    val message: String
)