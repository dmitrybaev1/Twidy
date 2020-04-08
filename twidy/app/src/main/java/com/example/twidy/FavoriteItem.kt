package com.example.twidy

data class FavoriteItem (
    var id: Int,
    var avatar: String,
    var personName: String,
    var description: String,
    var isVideoAccepted: Boolean,
    var isAudioAccepted: Boolean
){
    override fun equals(other: Any?): Boolean {
        var o: FavoriteItem = other as FavoriteItem
        return avatar==o.avatar&&personName==o.personName&&description==o.description&&isVideoAccepted==o.isVideoAccepted&&isAudioAccepted==o.isAudioAccepted
    }
}