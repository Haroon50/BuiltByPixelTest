package com.example.builtbypixeltest.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.builtbypixeltest.R
import com.example.builtbypixeltest.data.CoinResponse
import com.example.builtbypixeltest.domain.GenericResult
import com.example.builtbypixeltest.domain.nagivation.DetailScreenNavigation
import com.example.builtbypixeltest.domain.nagivation.HomeScreenNavigation
import com.example.builtbypixeltest.presentation.components.ErrorScreen
import com.example.builtbypixeltest.presentation.components.LoadingScreen
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
            is GenericResult.Success -> HomeScreen((uiState as GenericResult.Success<List<CoinResponse>>).data)
            is GenericResult.Error -> ErrorScreen(
                (uiState as GenericResult.Error).exception.message ?: "Unknown error"
            )
        }
    }

    @Composable
    fun HomeScreen(coins: List<CoinResponse>) {

        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = HomeScreenNavigation
        ) {
            composable<HomeScreenNavigation> {
                Column(
                    Modifier
                        .fillMaxSize()
                        .background(Color.White)
                ) {
                    LazyColumn(Modifier.weight(1f)) {
                        items(coins.size) {
                            CoinItem(coin = coins[it], navController)
                            HorizontalDivider()
                        }
                    }
                    Button(
                        onClick = { viewModel.fetchCoinData() },
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                    ) {
                        Text("Refresh")
                    }
                }
            }

            composable<DetailScreenNavigation> {
                val viewModel: CoinDetailViewModel by viewModels()
                val args = it.toRoute<DetailScreenNavigation>()
                DetailsScreen(data = args.coinId, viewModel)
            }
        }
    }

    @Composable
    fun CoinItem(coin: CoinResponse, navController: NavController) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    navController.navigate(
                        DetailScreenNavigation(
                            coinId = coin.id
                        )
                    )
                }) {
            Column(
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Row {
                    if (coin.type == "token") {
                        Image(
                            painter = painterResource(R.drawable.ic_token),
                            contentDescription = stringResource(id = R.string.token)
                        )
                    } else {
                        Image(
                            painter = painterResource(R.drawable.ic_coin),
                            contentDescription = stringResource(id = R.string.coin)
                        )
                    }
                    Text(text = coin.name, style = MaterialTheme.typography.bodyLarge)
                }
                Text(text = "(" + coin.symbol + ")")
                Text(
                    text = "Rank: ${coin.rank}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Blue
                )
            }
        }
    }
}