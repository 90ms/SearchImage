package com.a90ms.domain.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.a90ms.domain.base.PAGING_COUNT
import com.a90ms.domain.data.dto.ItemDto
import com.a90ms.domain.repository.NaverRepository
import javax.inject.Inject

class ImagePagingSource @Inject constructor(
    private val naverRepository: NaverRepository,
    private val query: String,
) : PagingSource<Int, ItemDto>() {
    override fun getRefreshKey(state: PagingState<Int, ItemDto>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ItemDto> {
        val page = params.key ?: 1
        return try {
            val start = if (page == 1) 1 else (page * PAGING_COUNT) + 1

            val dto = naverRepository.getImageList(query, start)
            val nextPage = page + 1
            LoadResult.Page(
                data = dto,
                prevKey = null,
                nextKey = if (dto.size < (PAGING_COUNT * page)) null else nextPage
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
