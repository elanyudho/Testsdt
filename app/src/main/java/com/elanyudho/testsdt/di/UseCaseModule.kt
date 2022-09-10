package com.elanyudho.testsdt.di

import com.elanyudho.testsdt.domain.repository.Repository
import com.elanyudho.testsdt.domain.usecase.JokesListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
object UseCaseModule {

    @Provides
    @ActivityScoped
    fun provideJokesListUseCase(repository: Repository) = JokesListUseCase(repository)
}