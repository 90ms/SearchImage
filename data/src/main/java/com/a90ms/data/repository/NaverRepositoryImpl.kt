package com.a90ms.data.repository

import com.a90ms.data.api.NaverService
import com.a90ms.domain.data.entity.ItemEntity
import com.a90ms.domain.repository.NaverRepository

class NaverRepositoryImpl(
    private val service: NaverService
) : NaverRepository {
    override suspend fun getImageList(query: String, display: Int, start: Int) =
        service.getImageList(query, display, start).run {
            this.items.map(ItemEntity::toDto)
        }
}
