package com.elanyudho.testsdt.di

import com.elanyudho.testsdt.data.remote.network.ApiService
import com.elanyudho.testsdt.data.remote.source.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
object DataSourceModule {
    @Provides
    @ActivityScoped
    fun provideRemoteDataSource(apiService: ApiService) = RemoteDataSource(apiService)
}