package com.example.twidy.entities

data class ConfirmData(
    var status: String,
    var result: ResultConfirmData,
    var code: Int,
    var message: String
)