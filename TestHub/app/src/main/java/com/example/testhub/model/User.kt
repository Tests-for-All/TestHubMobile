package com.example.testhub.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val userName: String,
    val mail: String,
    val login: String,
    val password: String
) : Parcelable