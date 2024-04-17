package com.example.testhub.model

import kotlinx.serialization.Serializable

@Serializable
data class Test (
    val id: Long,
    val name: String,
    val tags: List<Tag>
)

@Serializable
data class TestToCheck (
    val testId: Long,
    val questionResultCreateDtos: List<QuestionResultCreateDto>
)

@Serializable
data class QuestionResultCreateDto (
    val questionId: Long,
    val answerIdList: List<Long>
)