package com.example.testhub.model

import kotlinx.serialization.Serializable

@Serializable
data class Answer(
    var text: String,
    var isTrue: Boolean
)

@Serializable
data class AnswerHidden (
    val id: Long,
    val text: String
)

@Serializable
data class UserAnswer (
    val id: Long,
    val text: String,
    var isTrue: Boolean
)