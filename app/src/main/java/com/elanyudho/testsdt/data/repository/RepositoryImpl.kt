package com.elanyudho.testsdt.data.repository

import com.elanyudho.testsdt.data.remote.mapper.JokesMapper
import com.elanyudho.testsdt.data.remote.source.RemoteDataSource
import com.elanyudho.testsdt.domain.model.Jokes
import com.elanyudho.testsdt.domain.repository.Repository
import com.elanyudho.testsdt.utils.vo.Either
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val jokesMapper: JokesMapper
) : Repository {
    override suspend fun getJokes(query: String): Flow<List<Jokes>> {
        return flow {

            when (val response = remoteDataSource.getJokes(query)) {

                is Either.Success -> {
                    val listData = jokesMapper.mapToDomain(response.body.result)
                    emit(listData)

                }
                is Either.Error -> {
                    throw response.failure.throwable
                }
            }

        }
    }

}