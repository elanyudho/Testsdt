package com.elanyudho.testsdt.data.remote.source

import com.elanyudho.testsdt.utils.exception.Failure
import com.elanyudho.testsdt.utils.extension.hasEmptyBody
import com.elanyudho.testsdt.utils.extension.isTotallySuccess
import com.elanyudho.testsdt.utils.vo.Either
import com.elanyudho.testsdt.utils.vo.RequestResults
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

open class RemoteSafeRequest {

    open suspend fun <T> request(apiCall: suspend () -> Response<T>) : Either<Failure, T> {
        return try {
            val response = apiCall.invoke()

            when {
                response.isTotallySuccess() -> Either.Success(response.body()!!)
                response.hasEmptyBody() -> parseError(Failure((RequestResults.DATA_NOT_MATCH), Throwable("Empty Body")))
                response.code() in 300..599 -> parseError(Failure(RequestResults.SERVER_ERROR, Throwable("\"[${response.code()}] - [${response.message()}]\""),response.code().toString()))
                else -> parseError(Failure(RequestResults.UNKNOWN_ERROR, Throwable("\"Unknown error from server\"")))
            }
        } catch (throwable: Throwable) {
            // TODO 12/07/21 need add default message error custom
            when(throwable) {
                is UnknownHostException -> parseError(Failure(RequestResults.SERVER_ERROR,Throwable("No Internet Connection")))
                is ConnectException -> parseError(Failure(RequestResults.SERVER_ERROR,throwable))
                is SocketTimeoutException -> parseError(Failure(RequestResults.TIMEOUT,throwable))
                else -> parseError(Failure(RequestResults.UNKNOWN_ERROR,throwable))
            }
        }
    }

    private fun parseError(failure: Failure) : Either.Error<Failure> = Either.Error(failure)
}