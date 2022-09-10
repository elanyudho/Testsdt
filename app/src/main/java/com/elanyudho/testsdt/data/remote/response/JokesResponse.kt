package com.elanyudho.testsdt.data.remote.response

import com.google.gson.annotations.SerializedName

data class JokesResponse(
    @SerializedName("result")
    val result: List<Result> = listOf(),
    @SerializedName("total")
    val total: Int = 0
) {
    data class Result(
        @SerializedName("categories")
        val categories: List<Any> = listOf(),
        @SerializedName("created_at")
        val createdAt: String = "",
        @SerializedName("icon_url")
        val iconUrl: String = "",
        @SerializedName("id")
        val id: String = "",
        @SerializedName("updated_at")
        val updatedAt: String = "",
        @SerializedName("url")
        val url: String = "",
        @SerializedName("value")
        val value: String = ""
    )
}