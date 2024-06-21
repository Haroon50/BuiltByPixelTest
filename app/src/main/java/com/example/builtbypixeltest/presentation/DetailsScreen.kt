package com.example.builtbypixeltest.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.builtbypixeltest.data.CoinDetailResponse
import com.example.builtbypixeltest.domain.GenericResult
import com.example.builtbypixeltest.presentation.components.ErrorScreen
import com.example.builtbypixeltest.presentation.components.LoadingScreen

@Composable
fun DetailsScreen(data: String, viewModel: CoinDetailViewModel) {

    val uiState by viewModel.data.collectAsState()

    LaunchedEffect(data) {
        viewModel.fetchCoinDetail(data)
    }

    when (uiState) {
        is GenericResult.Loading -> LoadingScreen()
        is GenericResult.Success -> DetailContentScreen((uiState as GenericResult.Success<CoinDetailResponse>).data)
        is GenericResult.Error -> ErrorScreen(
            (uiState as GenericResult.Error).exception.message ?: "Unknown error"
        )
    }
}

@Composable
fun DetailContentScreen(coinData: CoinDetailResponse) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 24.dp, start = 16.dp, end = 16.dp, bottom = 24.dp)
    ) {

        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
            ),
            modifier = Modifier.fillMaxWidth()
        ) {

            Row(
                Modifier
                    .height(IntrinsicSize.Min)
                    .padding(16.dp)
            ) {

                AsyncImage(
                    model = coinData.logo,
                    contentDescription = "Coin Logo",
                    modifier = Modifier
                        .height(80.dp)
                        .width(80.dp)
                )

                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(start = 16.dp),
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = coinData.name,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }

            Column(Modifier.padding(16.dp)) {

                coinData.description?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                Text(text = "ID: ${coinData.id}", style = MaterialTheme.typography.bodyLarge)
                Text(text = "Type: ${coinData.type}", style = MaterialTheme.typography.bodyLarge)
                Text(
                    text = "symbol: ${coinData.symbol}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "Is  Active: ${coinData.is_active}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "Is New: ${coinData.is_new}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(text = "Rank: ${coinData.rank}", style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}

@Preview
@Composable
fun DetailScreen() {
    DetailContentScreen(
        coinData = CoinDetailResponse(
            id = "1",
            name = "name",
            symbol = "symbol",
            rank = 1,
            is_active = true,
            is_new = true,
            logo = "https://static.coinpaprika.com/coin/pod-pod-finance/logo.png",
            description = "some description",
            type = "type"
        )
    )
}