package com.a90ms.domain.data.entity

data class ResponseEntity(
    val lastBuildDate: String,
    val total: Long,
    val start: Int,
    val display: Int,
    val items: List<ItemEntity>
)
