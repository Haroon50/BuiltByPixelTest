package com.example.builtbypixeltest.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.builtbypixeltest.data.CoinDetailResponse
import com.example.builtbypixeltest.data.CoinRepository
import com.example.builtbypixeltest.domain.GenericResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(
    private val coinRepository: CoinRepository
)  : ViewModel() {

    private val _data = MutableStateFlow<GenericResult<CoinDetailResponse>>(GenericResult.Loading)
    val data: StateFlow<GenericResult<CoinDetailResponse>> get() = _data

    fun fetchCoinDetail(id: String) {
        viewModelScope.launch {
            _data.value = GenericResult.Loading
            val result = coinRepository.getCoinDetail(id)
            _data.value = result
        }
    }
}