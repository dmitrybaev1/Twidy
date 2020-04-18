package com.example.twidy.entities

import com.example.twidy.entities.ResultStaticData

data class StaticData(
    var status: String,
    var result: ResultStaticData,
    var code: Int,
    var message: String
)