package com.elanyudho.testsdt.domain.usecase

import com.elanyudho.testsdt.domain.model.Jokes
import com.elanyudho.testsdt.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class JokesListUseCase @Inject constructor(
    private val repository: Repository
) {

   suspend fun run(params: String) : Flow<List<Jokes>> {

        return repository.getJokes(params)
    }
}