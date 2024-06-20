package com.example.builtbypixeltest.view

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.builtbypixeltest.Utils.GenericResult
import com.example.builtbypixeltest.data.CoinResponse
import com.example.builtbypixeltest.model.Screen
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

        val uiState by viewModel.data.collectAsState()

        when (uiState) {
            is GenericResult.Loading -> LoadingScreen()
            is GenericResult.Success -> DataScreen((uiState as GenericResult.Success<List<CoinResponse>>).data)
            is GenericResult.Error -> ErrorScreen(
                (uiState as GenericResult.Error).exception.message ?: "Unknown error"
            )
        }
    }

    @Composable
    private fun LoadingScreen() {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator()
        }
    }

    @Composable
    fun DataScreen(coins: List<CoinResponse>) {

        Column(Modifier.fillMaxSize()) {
            LazyColumn(Modifier.weight(1f)) {
                items(coins.size) {
                    CoinItem(coin = coins[it])
                    HorizontalDivider()
                }
            }
            Button(
                onClick = { viewModel.fetchCoinData() },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                Text("Refresh Data")
            }
        }
    }

    @Composable
    fun ErrorScreen(errorMessage: String) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(text = errorMessage, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.error)
        }
    }

    @Composable
    fun CoinItem(coin: CoinResponse) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .clickable {

                }
        ) {
            Text(text = coin.name)
            Text(text = coin.type)
        }
    }
}
