package com.example.twidy.data.chats.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ChatEntity (
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "avatar")
    val avatar: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "lastMessage")
    val lastMessage: String,
    @ColumnInfo(name = "timestamp")
    val timestamp: Long,
    @ColumnInfo(name="newMessages")
    val newMessages: Int,
    @ColumnInfo(name = "type")
    val type: String
)
