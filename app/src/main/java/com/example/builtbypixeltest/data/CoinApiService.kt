package com.example.builtbypixeltest.data

import retrofit2.Response
import retrofit2.http.GET

interface CoinApiService {

    @GET("/coins")
    suspend fun fetchCoinData(): Response<CoinResponse>
}