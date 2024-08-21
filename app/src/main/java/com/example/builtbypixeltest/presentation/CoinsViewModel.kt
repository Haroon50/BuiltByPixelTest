package com.example.builtbypixeltest.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.builtbypixeltest.domain.GenericResult
import com.example.builtbypixeltest.data.CoinRepository
import com.example.builtbypixeltest.data.CoinResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinsViewModel @Inject constructor(
    private val coinRepository: CoinRepository
)  : ViewModel() {

    private val _data = MutableStateFlow<GenericResult<List<CoinResponse>>>(GenericResult.Loading)
    val data: StateFlow<GenericResult<List<CoinResponse>>> get() = _data

    init {
        fetchCoinData()
    }

    fun fetchCoinData() {
        viewModelScope.launch {
            val result = coinRepository.getAllCoinsData()
            _data.value = result
        }
    }
}