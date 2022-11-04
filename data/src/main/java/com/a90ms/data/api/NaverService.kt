package com.a90ms.data.api

import com.a90ms.domain.data.entity.ResponseEntity
import retrofit2.http.GET
import retrofit2.http.Query

interface NaverService {

    @GET("v1/search/image")
    suspend fun getImageList(
        @Query(QUERY) query: String,
        @Query(DISPLAY) display: Int,
        @Query(START) start: Int
    ): ResponseEntity

    companion object {
        const val QUERY = "query"

        /**
         * default = 1, max = 100
         * */
        const val DISPLAY = "display"

        /**
         * default = 1, max = 100
         * */
        const val START = "start"

        /**
         * default = sim, date
         * */
        const val SORT = "sort"

        /**
         * default = all, large, medium, small
         * */
        const val FILTER = "filter"
    }
}
