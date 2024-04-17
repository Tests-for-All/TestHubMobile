package com.example.testhub.model

import kotlinx.serialization.Serializable

@Serializable
data class QuestionAdd (
    val name: String,
    val details: String,
    val answerCreateDtos: List<Answer>
)

@Serializable
data class QuestionHidden (
    val id: Long,
    val name: String,
    val details: String,
    val answerHiddenDtos: List<AnswerHidden>
)

@Serializable
data class Question (
    val id: Long,
    val name: String,
    val details: String,
    val answerDtos: List<UserAnswer>
)