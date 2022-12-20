package com.adrian.airquality.di

import com.adrian.airquality.data.repository.AirQualityRepositoryImpl
import com.adrian.airquality.domain.repository.AirQualityRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindWeatherRepository(
        weatherRepositoryImpl: AirQualityRepositoryImpl
    ): AirQualityRepository
}