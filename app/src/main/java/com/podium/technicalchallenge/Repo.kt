package com.podium.technicalchallenge

import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.await
import com.podium.technicalchallenge.type.Sort


sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}

class Repo {

    suspend fun getMovies(): Result<GetMoviesQuery.Data?> {
        val response = ApiClient.getInstance().movieClient.query(GetMoviesQuery()).await()
        return if (response.data != null) {
            Result.Success(response.data)
        } else {
            Result.Error(java.lang.Exception())
        }
    }

    suspend fun topMovies(): Result<MovieSearchQuery.Data?> {
        val response = ApiClient.getInstance().movieClient.query(
            MovieSearchQuery(
                limit = Input.optional(5),
                orderBy = Input.optional("voteAverage"),
                sort = Input.optional(Sort.DESC)
            )
        ).await()
        return if (response.data != null) {
            Result.Success(response.data)
        } else {
            Result.Error(java.lang.Exception())
        }
    }

    companion object {
        private var INSTANCE: Repo? = null
        fun getInstance() = INSTANCE
            ?: Repo().also {
                INSTANCE = it
            }
    }
}