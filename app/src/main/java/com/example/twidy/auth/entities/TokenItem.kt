package com.example.twidy.auth.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TokenItem(
    val access_token: String,
    val call_id: String,
    val created: Int,
    val expires: Int
) : Parcelable
