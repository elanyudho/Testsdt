package com.elanyudho.testsdt.data.remote.network

import com.elanyudho.testsdt.data.remote.response.JokesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("search")
    suspend fun getJokes(
        @Query("query") String: String
    ): Response<JokesResponse>
}