package com.example.twidy.ui.chats.items

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ChatItem (
    @PrimaryKey
    var id: Int,
    @ColumnInfo(name = "avatar")
    var avatar: String,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "lastMessage")
    var lastMessage: String,
    @ColumnInfo(name = "timestamp")
    var timestamp: Long,
    @ColumnInfo(name="newMessages")
    var newMessages: Int,
    @ColumnInfo(name = "checked")
    var checked: Boolean,
    @ColumnInfo(name = "inCheckedMode")
    var inCheckedMode: Boolean,
    @ColumnInfo(name = "type")
    var type: String
) {
    override fun equals(other: Any?): Boolean {
        val o = other as ChatItem
        return id==o.id&&avatar==o.avatar&&name==o.name&&lastMessage==o.lastMessage&&
                newMessages==o.newMessages&&checked==o.checked&&inCheckedMode==o.inCheckedMode
                &&type==o.type
    }
}