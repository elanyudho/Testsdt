package com.elanyudho.testsdt.di

import com.elanyudho.testsdt.data.remote.mapper.JokesMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
object MapperModule {

    @Provides
    @ActivityScoped
    fun provideJokesMapper() = JokesMapper()
}