package com.example.twidy.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

data class Chat (
    var id: Int,
    var last_message: LastMessage,
    var peer: Peer,
    var balance_actual: Int
)