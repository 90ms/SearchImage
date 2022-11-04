package com.a90ms.domain.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.a90ms.domain.base.CommonDtoUseCase
import com.a90ms.domain.base.PAGING_COUNT
import com.a90ms.domain.data.dto.ItemDto
import com.a90ms.domain.di.IoDispatcher
import com.a90ms.domain.pagingsource.ImagePagingSource
import com.a90ms.domain.repository.NaverRepository
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class GetImageListUseCase @Inject constructor(
    private val repository: NaverRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : CommonDtoUseCase<String, Flow<PagingData<ItemDto>>>(dispatcher) {
    override suspend fun execute(parameters: String) = Pager(
        config = PagingConfig(
            pageSize = PAGING_COUNT,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            ImagePagingSource(repository, parameters)
        }
    ).flow
}
