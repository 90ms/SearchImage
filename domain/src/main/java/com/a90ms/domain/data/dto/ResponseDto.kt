package com.a90ms.domain.data.dto

data class ResponseDto(
    val lastBuildDate: String,
    val total: Long,
    val start: Int,
    val display: Int,
    val items: List<ItemDto>
)
