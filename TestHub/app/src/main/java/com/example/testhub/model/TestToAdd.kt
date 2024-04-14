package com.example.testhub.model

import kotlinx.serialization.Serializable
@Serializable
data class TestToAdd (
    val name: String,
    val tags: List<Tag>,
    val questionCreateDtos: List<Question>
)
