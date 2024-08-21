package com.example.builtbypixeltest.data

import com.example.builtbypixeltest.domain.GenericResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

@ExperimentalCoroutinesApi
class CoinRepositoryTest {

    private lateinit var apiService: CoinApiService
    private lateinit var repository: CoinRepository

    @Before
    fun setUp() {
        apiService = mock(CoinApiService::class.java)
        repository = CoinRepository(apiService)
    }

    @Test
    fun getAllCoinsData_success() = runTest {
        // Given
        val expected = listOf(
            CoinResponse("1", "Bitcoin", "symbol",  1, true, is_active = true, type = "type"),
            CoinResponse("2", "AlphaCoin", "symbol",  1, true, is_active = true, type = "type")
        )

        // When
        `when`(apiService.fetchAllCoinsData()).thenReturn(expected)
        val actual = repository.getAllCoinsData()

        // Then
        assertTrue(actual is GenericResult.Success)
        assertEquals((actual as GenericResult.Success).data, expected.sortedBy { it.name })
    }

    @Test
    fun getAllCoinsData_error() = runTest {
        // Given
        val expected = RuntimeException("Error fetching data")
        `when`(apiService.fetchAllCoinsData()).thenThrow(expected)

        // When
        val actual = repository.getAllCoinsData()

        // Then
        assertTrue(actual is GenericResult.Error)
        assertEquals((actual as GenericResult.Error).exception.message, expected.message)
    }
}