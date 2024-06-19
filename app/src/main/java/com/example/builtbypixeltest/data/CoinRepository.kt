package com.example.builtbypixeltest.data

import javax.inject.Inject

class CoinRepository @Inject constructor(
    private val coinApiService: CoinApiService
) {
    suspend fun getCoinData(): List<CoinResponse> {
        return coinApiService.fetchCoinData()
    }
}