package com.example.twidy.entities

import android.os.Parcelable
import com.example.twidy.entities.LocalUser
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResultConfirmData(
    var access_token: String,
    var call_id: String,
    var created: Int,
    var expires: Int,
    var localUser: LocalUser
) : Parcelable