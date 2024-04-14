package com.example.testhub.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Question (
    val name: String,
    val details: String,
    val answerCreateDtos: List<Answer>
)