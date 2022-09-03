package com.example.twidy.data.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LocalUser (
    var id: Int,
    var domain: String?,
    var email: String?,
    var balance: Int,
    var last_activity: Int,
    var is_verify: Boolean,
    var is_online: Boolean,
    var is_blocked: Boolean,
    var guuid: String?,
    var phone: String,
    var withdraw: String?
) : Parcelable