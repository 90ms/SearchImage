package com.a90ms.data.repository

import com.a90ms.common.dto.CommonDto
import com.a90ms.data.api.NaverService
import com.a90ms.domain.repository.NaverRepository

class NaverRepositoryImpl(
    private val service: NaverService
) : NaverRepository {
    override suspend fun getImageList(query: String) = service.getImageList(query).run {
        CommonDto(code = null, errorMessage = null, data = this.toDto())
    }
}
