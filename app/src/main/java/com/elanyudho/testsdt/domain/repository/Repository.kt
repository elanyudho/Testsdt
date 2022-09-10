package com.elanyudho.testsdt.domain.repository

import com.elanyudho.testsdt.domain.model.Jokes
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun getJokes(query: String): Flow<List<Jokes>>
}