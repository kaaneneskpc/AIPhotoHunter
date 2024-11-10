package com.example.aiphotohunter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.aiphotohunter.navigation.NavGraph
import com.example.aiphotohunter.screens.PredictionViewModel
import com.example.aiphotohunter.screens.hunt.HuntViewModel
import com.example.aiphotohunter.ui.theme.AIPhotoHunterTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val huntViewModel: HuntViewModel by viewModels()
    private val predictionViewModel: PredictionViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PhotoHuntApp(huntViewModel, predictionViewModel)
        }
    }
}

@Composable
fun PhotoHuntApp(huntViewModel: HuntViewModel, predictionViewModel: PredictionViewModel) {
    AIPhotoHunterTheme {
        NavGraph(huntViewModel = huntViewModel, predictionViewModel = predictionViewModel)
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    val huntViewModel = HuntViewModel()
    val predictionViewModel = PredictionViewModel()
    PhotoHuntApp(huntViewModel, predictionViewModel)
}
