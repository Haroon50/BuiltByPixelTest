package com.example.builtbypixeltest.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Creates a CoroutineScope bound to the MainScreen lifecycle
        setContent {
            MainScreen()
        }
    }

    @Composable
    fun MainScreen() {

        val scope = rememberCoroutineScope()
        
        Text(text = "New Work")
        LazyColumn {

            scope.launch {

//                items(coins) { coin  ->
//                    coinItem(coins = coin)
//                }
            }

        }

    }


//    @Composable
//    fun coinItem(coins: CoinItem) {
//        Card {
//            Column {
//                Text(text = "Bitcoin")
//                Text(text = "BTC")
//            }
//        }
//    }
}