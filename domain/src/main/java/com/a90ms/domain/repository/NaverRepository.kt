package com.a90ms.domain.repository

import com.a90ms.common.dto.CommonDto
import com.a90ms.domain.data.dto.ResponseDto

interface NaverRepository {
    suspend fun getImageList(
        query: String
    ): CommonDto<ResponseDto>
}
