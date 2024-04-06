package com.example.testhub.model

import android.os.Parcelable
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val username: String,
    val email: String,
    val password: String
)