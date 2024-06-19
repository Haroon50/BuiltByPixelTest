package com.example.builtbypixeltest.view

import androidx.lifecycle.ViewModel
import com.example.builtbypixeltest.data.CoinRepository
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CoinsViewModel @Inject constructor(
    private val coinRepository: CoinRepository
)  : ViewModel() {

    init {
       // coinRepository.getCoinData()
    }

}