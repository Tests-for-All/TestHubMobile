package com.example.testhub.model

import kotlinx.serialization.Serializable

@Serializable
data class Test (
    val id: Long,
    val name: String
)
