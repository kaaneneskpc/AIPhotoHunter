package com.example.aiphotohunter.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.aiphotohunter.components.CircularProgressBar
import com.example.aiphotohunter.navigation.HandleBackPressToHome
import com.example.aiphotohunter.navigation.Screen
import com.example.aiphotohunter.screens.hunt.HuntViewModel

@Composable
fun HuntFinishScreen(navController: NavHostController, huntViewModel: HuntViewModel) {
    HandleBackPressToHome(navController, huntViewModel)

    val winPercentage by huntViewModel.winPercentage.collectAsState()
    val winRatePercentage by huntViewModel.winRatePercentage.collectAsState()
    val finishText by huntViewModel.finishText.collectAsState()
    val yourScoreText by huntViewModel.yourScoreText.collectAsState()
    val winRateText by huntViewModel.winRateText.collectAsState()
    val playAgainText by huntViewModel.playAgainText.collectAsState()
    val emoji by huntViewModel.emoji.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = emoji, style = MaterialTheme.typography.displayLarge.copy(
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 65.sp
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                Text(
                    text = finishText,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Medium)
                )
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = yourScoreText,
                    style = MaterialTheme.typography.displaySmall.copy(
                        fontWeight = FontWeight.W600
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = winRateText,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium)
                )
                Spacer(modifier = Modifier.height(16.dp))
                CircularProgressBar(
                    percentage = winPercentage,
                    number = winRatePercentage
                )
            }

            Spacer(modifier = Modifier.height(50.dp))
        }

        Button(
            onClick = {
                huntViewModel.reset()
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Home.route) { inclusive = true }
                }
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 35.dp)
        ) {
            Text(text = playAgainText)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HuntFinishScreenPreview() {
    val navController = rememberNavController()
    val huntViewModel = HuntViewModel().apply {
        _score.value = 350
        _correctAnswers.value = 10
        setSelectedLanguage("Türkçe")
    }
    HuntFinishScreen(navController, huntViewModel)
}