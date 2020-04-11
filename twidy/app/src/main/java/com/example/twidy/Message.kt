package com.example.twidy

data class Message (
    var id: Int,
    var dialog_id: Int,
    var user_id: Int,
    var message: String,
    var type: String,
    var timestamp: Long,
    var fomatted_time: String
){
    override fun equals(other: Any?): Boolean {
        var o = other as Message
        return id==o.id&&dialog_id==o.dialog_id&&user_id==o.user_id&&message==o.message&&type==o.type
                &&timestamp==o.timestamp&&fomatted_time==o.fomatted_time
    }
}