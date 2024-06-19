package com.example.builtbypixeltest.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.builtbypixeltest.data.CoinResponse
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: CoinsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MainScreen()
        }
    }

    @Composable
    fun MainScreen() {
        val coins = viewModel.coinData.collectAsState()
        viewModel.fetchCoinData()

        Column(Modifier.fillMaxSize()) {
            LazyColumn(Modifier.weight(1f)) {
                items(coins.value) { coin ->
                    CoinItem(coin = coin)
                    HorizontalDivider()
                }
            }
            Button(
                onClick = { viewModel.fetchCoinData() },
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
            ) {
                Text("Refresh Data")
            }
        }
    }

    @Composable
    fun CoinItem(coin: CoinResponse) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(text = coin.name)
            Text(text = coin.type)
        }
    }
}
