package com.elanyudho.testsdt.di

import com.elanyudho.testsdt.data.repository.RepositoryImpl
import com.elanyudho.testsdt.domain.repository.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
abstract class RepositoryModule {

    @Binds
    @ActivityScoped
    abstract fun bindRepository(repositoryImpl: RepositoryImpl): Repository

}