package com.a90ms.domain.data.entity

import com.a90ms.domain.data.dto.ResponseDto

data class ResponseEntity(
    val lastBuildDate: String,
    val total: Long,
    val start: Int,
    val display: Int,
    val items: List<ItemEntity>
) {
    fun toDto() = ResponseDto(
        lastBuildDate = lastBuildDate,
        total = total,
        start = start,
        display = display,
        items = items.map(ItemEntity::toDto)
    )
}
