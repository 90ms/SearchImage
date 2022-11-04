package com.a90ms.domain.repository

import com.a90ms.domain.data.dto.ItemDto

interface NaverRepository {
    suspend fun getImageList(
        query: String,
        display: Int,
        start: Int
    ): List<ItemDto>
}
