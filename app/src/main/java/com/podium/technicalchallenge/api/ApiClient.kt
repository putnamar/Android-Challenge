package com.podium.technicalchallenge.api


import com.apollographql.apollo.ApolloClient
import okhttp3.OkHttpClient


class ApiClient {
    val API_URL = "https://podium-fe-challenge-2021.netlify.app/.netlify/functions/graphql"

    companion object {
        private var INSTANCE: ApiClient? = null
        fun getInstance() = INSTANCE
            ?: ApiClient().also {
                INSTANCE = it
            }
    }

    val movieClient: ApolloClient = ApolloClient.builder()
        .serverUrl(API_URL)
        .okHttpClient(
            OkHttpClient().newBuilder()
                .build()
        )
        .build()

}