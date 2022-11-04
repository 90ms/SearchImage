package com.a90ms.domain.base

import com.a90ms.common.dto.CommonDto
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException

abstract class CommonDtoUseCase<in P, R : Any>(
    private val coroutineDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(parameters: P): Result<R?> {
        return try {
            withContext(coroutineDispatcher) {
                execute(parameters).let {
                    it.data?.let { Result.Success(it) } ?: run {
                        Result.Error(errorCode = it.code ?: "", message = it.errorMessage ?: "")
                    }
                }
            }
        } catch (e: HttpException) {
            Result.Error(e.code(), "", e.message())
        } catch (e: Exception) {
            Result.Exception(e)
        }
    }

    @Throws(RuntimeException::class)
    protected abstract suspend fun execute(parameters: P): CommonDto<R>
}
