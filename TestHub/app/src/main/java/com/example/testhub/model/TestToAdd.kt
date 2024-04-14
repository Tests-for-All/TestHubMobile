package com.example.testhub.model

import kotlinx.serialization.Serializable
@Serializable
data class TestToAdd (
    val name: String,
    val tagListDtos: List<Tag>,
    val questionCreateDtos: List<Question>
)
