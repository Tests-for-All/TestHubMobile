package com.example.testhub.model

import kotlinx.serialization.Serializable

@Serializable
data class Answer(
    var text: String,
    var isTrue: Boolean
)
