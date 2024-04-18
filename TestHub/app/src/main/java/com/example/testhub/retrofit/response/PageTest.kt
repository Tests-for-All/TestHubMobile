package com.example.testhub.retrofit.response

import com.example.testhub.model.Test
import kotlinx.serialization.Serializable
@Serializable
data class PageTest (
    val content: List<Test>,
    val pageable: Pageable,
    val last: Boolean,
    val totalPages: Long,
    val totalElements: Long,
    val first: Boolean,
    val sort: Sort,
    val size: Long,
    val number: Long,
    val numberOfElements: Long,
    val empty: Boolean
)
@Serializable
data class Pageable (
    val pageNumber: Long,
    val pageSize: Long,
    val sort: Sort,
    val offset: Long,
    val paged: Boolean,
    val unpaged: Boolean
)

@Serializable
data class Sort (
    val sorted: Boolean,
    val empty: Boolean,
    val unsorted: Boolean
)