package com.example.testhub.retrofit.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JwtResponse(
    @SerialName("token")
    val token: String
)
