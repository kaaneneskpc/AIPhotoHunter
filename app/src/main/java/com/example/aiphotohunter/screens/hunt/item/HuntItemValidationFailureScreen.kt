package com.example.aiphotohunter.screens.hunt.item

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.aiphotohunter.R
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
    val itemCount by huntViewModel.itemsLeft.collectAsState()
    val failureEmoji by huntViewModel.failureEmoji.collectAsState()
    val skipButtonText by huntViewModel.skipButtonText.collectAsState()
    val shouldRetry = predictionViewModel.shouldRetry()
    val failureMessage = huntViewModel.getFailureMessage(shouldRetry)
    val retryButtonText = huntViewModel.getRetryButtonText(shouldRetry)

    LaunchedEffect(itemCount) {
        if (itemCount == 0) {
            navController.navigate(Screen.Finish.route) {
                popUpTo(Screen.Finish.route) { inclusive = true }
            }
        }
    }
    HandleBackPressToHome(navController, huntViewModel)

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.image),
            contentDescription = "Arka Plan",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

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
                Text(text = failureEmoji, style = MaterialTheme.typography.displayLarge)

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = failureMessage,
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
                    Text(text = skipButtonText)
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(onClick = {
                    if (shouldRetry) {
                        predictionViewModel.incrementRetryCount()
                        predictionViewModel.resetPrediction()
                        navController.navigate(Screen.Pending.route)
                    } else {
                        predictionViewModel.resetRetryCount()
                        huntViewModel.pickNextItem()
                        navController.navigate(Screen.Pending.route)
                    }
                }) {
                    Text(text = retryButtonText)
                }
            }
            Spacer(modifier = Modifier.height(35.dp))
        }
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