package com.example.builtbypixeltest.data

import com.example.builtbypixeltest.Utils.GenericResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CoinRepository @Inject constructor(
    private val coinApiService: CoinApiService
) {

    suspend fun getCoinData(): GenericResult<List<CoinResponse>> {
        return try {
            val response = withContext(Dispatchers.IO) {
                coinApiService.fetchCoinData()
            }
            GenericResult.Success(response)
        } catch (e: Exception) {
            GenericResult.Error(e)
        }
    }
}