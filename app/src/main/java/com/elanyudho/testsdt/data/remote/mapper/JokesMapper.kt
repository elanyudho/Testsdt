package com.elanyudho.testsdt.data.remote.mapper

import com.elanyudho.testsdt.data.remote.response.JokesResponse
import com.elanyudho.testsdt.domain.model.Jokes

class JokesMapper {

   fun mapToDomain(raw: List<JokesResponse.Result>): List<Jokes> {
        return raw.map {
            Jokes(id = it.id, createAt = it.createdAt, icon = it.iconUrl, value = it.value)
        }
    }

}