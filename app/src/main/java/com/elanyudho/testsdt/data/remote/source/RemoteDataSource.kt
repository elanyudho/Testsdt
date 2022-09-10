package com.elanyudho.testsdt.data.remote.source

import com.elanyudho.testsdt.data.remote.network.ApiService
import com.elanyudho.testsdt.data.remote.response.JokesResponse
import com.elanyudho.testsdt.utils.exception.Failure
import com.elanyudho.testsdt.utils.vo.Either
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val api: ApiService) : RemoteSafeRequest() {

    suspend fun getJokes(query: String): Either<Failure, JokesResponse> =
        request {
            api.getJokes(query)
        }

}