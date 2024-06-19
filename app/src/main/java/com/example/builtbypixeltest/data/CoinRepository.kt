package com.example.builtbypixeltest.data

import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

class CoinRepository @Inject constructor(
    private val coinApiService: CoinApiService
){
    suspend fun getCoinData(){

        coinApiService.fetchCoinData()
    }
}