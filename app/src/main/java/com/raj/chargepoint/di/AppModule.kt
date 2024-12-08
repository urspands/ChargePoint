package com.raj.chargepoint.di

import com.raj.chargepoint.data.repo.SchedulerRepositoryImpl
import com.raj.chargepoint.data.SchedulerService
import com.raj.chargepoint.data.SchedulerServiceImpl
import com.raj.chargepoint.domain.SchedulerRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providesSchedulerService(): SchedulerService {
        return SchedulerServiceImpl()
    }

    @Module
    @InstallIn(SingletonComponent::class)
    abstract class DataRepositoryModule {
        @Binds
        @Singleton
        abstract fun bindDataRepository(dataRepositoryImpl: SchedulerRepositoryImpl): SchedulerRepository
    }

}