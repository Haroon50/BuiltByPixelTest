package com.example.builtbypixeltest.data

import com.example.builtbypixeltest.domain.GenericResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CoinRepository @Inject constructor(
    private val coinApiService: CoinApiService
) {

    suspend fun getAllCoinsData(): GenericResult<List<CoinResponse>> {
        return try {
            val response = withContext(Dispatchers.IO) {
                coinApiService.fetchAllCoinsData().sortedBy { it.name }
            }
            GenericResult.Success(response)
        } catch (e: Exception) {
            GenericResult.Error(e)
        }
    }

    suspend fun getCoinDetail(id:String): GenericResult<CoinDetailResponse> {
        return try {
            val response = withContext(Dispatchers.IO) {
                coinApiService.fetchCoinDetail(id)
            }
            GenericResult.Success(response)
        } catch (e: Exception) {
            GenericResult.Error(e)
        }
    }
}