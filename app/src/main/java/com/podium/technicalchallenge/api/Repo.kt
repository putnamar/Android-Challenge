package com.podium.technicalchallenge.api

import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.await
import com.podium.technicalchallenge.*
import com.podium.technicalchallenge.type.Sort


sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}

class Repo {

    suspend fun getMovie(movieId: Int): Result<GetMovieQuery.Data?> {
        val response = ApiClient.getInstance().movieClient.query(
            GetMovieQuery(id = movieId)
        ).await()
        return if (response.data != null) {
            Result.Success(response.data)
        } else {
            Result.Error(java.lang.Exception())
        }
    }

    suspend fun getMoviePage(
        offset: Int,
        size: Int,
        sortBy: String,
        ascending: Boolean,
        searchTerms: List<String>?
    ): Result<GetMoviesPageQuery.Data?> {
        val search = if (searchTerms.isNullOrEmpty()) Input.absent() else Input.optional(
            searchTerms.joinToString(
                separator = "|",
                prefix = "(",
                postfix = ")"
            )
        )

        val response = ApiClient.getInstance().movieClient.query(
            GetMoviesPageQuery(
                offset = Input.optional(offset),
                limit = Input.optional(size),
                orderBy = Input.optional(sortBy),
                sort = Input.optional(if (ascending) Sort.ASC else Sort.DESC),
                search = search
            )
        ).await()
        return if (response.data != null) {
            Result.Success(response.data)
        } else {
            Result.Error(java.lang.Exception())
        }
    }

    suspend fun getMoviesByGenre(
        genre: String,
        sortBy: String,
        ascending: Boolean
    ): Result<GenreSearchQuery.Data?> {
        val response = ApiClient.getInstance().movieClient.query(
            GenreSearchQuery(
                genre = Input.optional(genre),
                orderBy = Input.optional(sortBy),
                limit = Input.optional(100),
                sort = Input.optional(if (ascending) Sort.ASC else Sort.DESC)
            )
        ).await()
        return if (response.data != null) {
            Result.Success(response.data)
        } else {
            Result.Error(java.lang.Exception())
        }
    }

    suspend fun getGenres(): Result<GetAllGenresQuery.Data?> {
        val response = ApiClient.getInstance().movieClient.query(GetAllGenresQuery()).await()
        return if (response.data != null) {
            Result.Success(response.data)
        } else {
            Result.Error(java.lang.Exception())
        }
    }

    suspend fun topMovies(count: Int = 5): Result<MovieSearchQuery.Data?> {
        val response = ApiClient.getInstance().movieClient.query(
            MovieSearchQuery(
                limit = Input.optional(count),
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