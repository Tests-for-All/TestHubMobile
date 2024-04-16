package com.example.testhub.model

import kotlinx.serialization.Serializable

@Serializable
data class Answer(
    var text: String,
    var isTrue: Boolean
)

@Serializable
data class AnswerGet (
    val id: Long,
    val text: String,
    var isTrue: Boolean
)