package com.a90ms.data.di

import com.a90ms.data.api.NaverService
import com.a90ms.data.repository.NaverRepositoryImpl
import com.a90ms.domain.repository.NaverRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideNaverRepository(
        naverService: NaverService
    ): NaverRepository = NaverRepositoryImpl(naverService)
}