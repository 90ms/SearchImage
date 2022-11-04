package com.a90ms.domain.repository

interface NaverRepository {
    suspend fun getImageList(
        query: String
    )
}