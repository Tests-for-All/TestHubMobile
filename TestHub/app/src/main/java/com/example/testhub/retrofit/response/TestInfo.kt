package com.example.testhub.retrofit.response

import com.example.testhub.model.Question
import com.example.testhub.model.Tag
import kotlinx.serialization.Serializable
@Serializable
data class TestInfo (
    val id: Long,
    val name: String,
    val user: User,
    val tags: List<Tag>,
    val questionListDtos: List<Question>
)

@Serializable
data class User (
    val id: Long,
    val username: String,
    val email: String
)