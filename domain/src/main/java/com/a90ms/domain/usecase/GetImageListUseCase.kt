package com.a90ms.domain.usecase

import com.a90ms.common.dto.CommonDto
import com.a90ms.domain.base.CommonDtoUseCase
import com.a90ms.domain.data.dto.ResponseDto
import com.a90ms.domain.di.IoDispatcher
import com.a90ms.domain.repository.NaverRepository
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher

class GetImageListUseCase @Inject constructor(
    private val repository: NaverRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : CommonDtoUseCase<String, ResponseDto>(dispatcher) {
    override suspend fun execute(parameters: String): CommonDto<ResponseDto> {
        return repository.getImageList(parameters)
    }
}
