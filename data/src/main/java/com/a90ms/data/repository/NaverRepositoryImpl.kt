package com.a90ms.data.repository

import com.a90ms.data.api.NaverService
import com.a90ms.domain.data.entity.ItemEntity
import com.a90ms.domain.repository.NaverRepository

class NaverRepositoryImpl(
    private val service: NaverService
) : NaverRepository {
    override suspend fun getImageList(query: String, start: Int) =
        service.getImageList(query, start).run {
            this.items.map(ItemEntity::toDto)
        }
}
