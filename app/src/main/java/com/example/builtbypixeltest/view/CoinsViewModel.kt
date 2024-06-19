package com.example.builtbypixeltest.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.builtbypixeltest.data.CoinRepository
import com.example.builtbypixeltest.data.CoinResponse
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinsViewModel @Inject constructor(
    private val coinRepository: CoinRepository
)  : ViewModel() {

    private val _coinData = MutableStateFlow(listOf(CoinResponse("","","",1,true,true, "")))
    val coinData = _coinData.asStateFlow()


    fun fetchCoinData(){
        viewModelScope.launch {
            val data = coinRepository.getCoinData()
            _coinData.value = data
        }
    }
}