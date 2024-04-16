package com.example.testhub.model

import kotlinx.serialization.Serializable

@Serializable
data class QuestionAdd (
    val name: String,
    val details: String,
    val answerCreateDtos: List<Answer>
)

@Serializable
data class QuestionGet (
    val id: Long,
    val name: String,
    val details: String,
    val answerDtos: List<AnswerGet>
)