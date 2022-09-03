package com.example.twidy.data.response

data class ConfirmData(
    var status: String,
    var result: ResultConfirmData,
    var code: Int,
    var message: String
)