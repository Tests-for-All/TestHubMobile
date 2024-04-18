package com.example.testhub.retrofit.response

import com.example.testhub.model.Tag
import kotlinx.serialization.Serializable
@Serializable
data class ResultTest (
    val id: Long,
    val testListDto: TestListDto,
    val userDto: UserDto,
    val testResultInformationListDto: TestResultInformationListDto
)

@Serializable
data class TestListDto (
    val id: Long,
    val name: String,
    val tags: List<Tag>
)

@Serializable
data class TestResultInformationListDto (
    val id: Long,
    val percentageCorrectAnswers: Double
)

@Serializable
data class UserDto (
    val id: Long,
    val username: String,
    val email: String
)
