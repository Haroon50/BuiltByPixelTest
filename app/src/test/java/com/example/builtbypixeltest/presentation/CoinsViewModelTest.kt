package com.example.builtbypixeltest.presentation

import com.example.builtbypixeltest.data.CoinRepository
import com.example.builtbypixeltest.data.CoinResponse
import com.example.builtbypixeltest.domain.GenericResult
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

@ExperimentalCoroutinesApi
class CoinsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var viewModel: CoinsViewModel

    @Mock
    private lateinit var coinRepository: CoinRepository

    @Before
    fun setUp() = runTest {
        coinRepository = mock(CoinRepository::class.java)
        viewModel = CoinsViewModel(coinRepository)

    }

    @Test
    fun fetchCoinData_success() = runTest {
        // Given
        val coinList = listOf(CoinResponse("Bitcoin", "BTC", "symbol",  1, true, is_active = true, type = "type"))

        val expectedResult = GenericResult.Success(coinList)
        `when`(coinRepository.getAllCoinsData()).thenReturn(expectedResult)

        // When
        viewModel.fetchCoinData()

        // Then
        val result = viewModel.data.value
        assertTrue(result is GenericResult.Success)
        assertEquals((result as GenericResult.Success).data, coinList)
    }

    @Test
    fun fetchCoinData_error() = runTest {
        // Given
        val exception = RuntimeException("Error fetching data")
        val expectedResult = GenericResult.Error(exception)
        `when`(coinRepository.getAllCoinsData()).thenReturn(expectedResult)

        // When
        viewModel.fetchCoinData()

        // Then
        val result = viewModel.data.value
        assertTrue(result is GenericResult.Error)
        assertEquals((result as GenericResult.Error).exception, exception)
    }


    @Test
    fun fetchCoinData_ordering() = runTest {
        // Given
        val coinList = listOf(
            CoinResponse("1", "Bitcoin", "symbol",  1, true, is_active = true, type = "type"),
            CoinResponse("2", "AlphaCoin", "symbol",  1, true, is_active = true, type = "type"),
            CoinResponse("3", "DeltaCoin", "symbol",  1, true, is_active = true, type = "type")
        )

        val expected = GenericResult.Success(coinList.sortedBy { it.name })
        `when`(coinRepository.getAllCoinsData()).thenReturn(expected)

        // When
        viewModel.fetchCoinData()

        // Then
        val actual = viewModel.data.value
        assertTrue(actual is GenericResult.Success)
        assertEquals(expected, actual)
    }
}