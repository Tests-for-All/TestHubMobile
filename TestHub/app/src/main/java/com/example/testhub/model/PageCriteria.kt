package com.example.testhub.model

import kotlinx.serialization.Serializable

@Serializable
data class PageCriteria(
    var name: String?,
    var tagIdList: List<Long>?,
    var creatorName: String?
)
