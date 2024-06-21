package com.example.builtbypixeltest.data

import retrofit2.http.GET
import retrofit2.http.Path

interface CoinApiService {

    @GET("coins")
    suspend fun fetchAllCoinsData(): List<CoinResponse>

    @GET("coins/{id}")
    suspend fun fetchCoinDetail(@Path("id") id: String): CoinDetailResponse

}