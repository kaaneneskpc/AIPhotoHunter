package com.example.aiphotohunter.screens.hunt.item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.aiphotohunter.components.HuntProgress
import com.example.aiphotohunter.navigation.HandleBackPressToHome
import com.example.aiphotohunter.navigation.Screen
import com.example.aiphotohunter.screens.PredictionViewModel
import com.example.aiphotohunter.screens.hunt.HuntViewModel

@Composable
fun HuntItemValidationFailureScreen(
    navController: NavHostController,
    huntViewModel: HuntViewModel,
    predictionViewModel: PredictionViewModel
) {

    HandleBackPressToHome(navController, huntViewModel)

    val retryCount = remember { mutableStateOf(predictionViewModel.shouldRetry()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(15.dp))

        HuntProgress(huntViewModel)

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Text(text = "🫠", style = MaterialTheme.typography.displayLarge)

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = if (retryCount.value) {
                    "Nope, doesn't look like it."
                } else {
                    "Nope, doesn't look like it. You've reached your retry limit. Moving to the next item."
                },
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.W500),
                textAlign = TextAlign.Center
            )
        }

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            OutlinedButton(onClick = {
                huntViewModel.pickNextItem()
                predictionViewModel.resetRetryCount()
                navController.navigate(Screen.Pending.route)
            }) {
                Text(text = "Skip")
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(onClick = {
                if (retryCount.value) {
                    predictionViewModel.incrementRetryCount()
                    predictionViewModel.resetPrediction()
                    navController.navigate(Screen.Pending.route)
                } else {
                    predictionViewModel.resetRetryCount()
                    huntViewModel.pickNextItem()
                    navController.navigate(Screen.Pending.route)
                }
            }) {
                Text(text = if (retryCount.value) "Try again" else "Next")
            }
        }
        Spacer(modifier = Modifier.height(35.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun ItemValidationFailureScreenPreview() {
    val navController = rememberNavController()
    val huntViewModel = HuntViewModel()
    val predictionViewModel = PredictionViewModel()
    HuntItemValidationFailureScreen(navController, huntViewModel, predictionViewModel)
}