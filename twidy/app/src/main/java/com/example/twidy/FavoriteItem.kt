package com.example.twidy

data class FavoriteItem (
    var avatar: String,
    var personName: String,
    var description: String,
    var isVideoAvailable: Boolean,
    var isAudioAvailable: Boolean
){
    override fun equals(other: Any?): Boolean {
        var o: FavoriteItem = other as FavoriteItem
        return avatar==o.avatar&&personName==o.personName&&description==o.description&&isVideoAvailable==o.isVideoAvailable&&isAudioAvailable==o.isAudioAvailable
    }
}