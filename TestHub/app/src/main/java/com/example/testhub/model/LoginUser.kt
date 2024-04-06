package com.example.testhub.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginUser (
    val username: String,
    val password: String
)