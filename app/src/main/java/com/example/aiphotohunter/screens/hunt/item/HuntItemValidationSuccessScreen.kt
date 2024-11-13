package com.example.aiphotohunter.screens.hunt.item

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
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
fun HuntItemValidationSuccessScreen(
    navController: NavHostController,
    huntViewModel: HuntViewModel,
    predictionViewModel: PredictionViewModel
) {

    HandleBackPressToHome(navController, huntViewModel)

    val itemCount = huntViewModel.itemsLeft.collectAsState()
    val reward = 50

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
            Text(text = "🤩", style = MaterialTheme.typography.displayLarge)

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "You found it!",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "+$reward",
                style = MaterialTheme.typography.displayMedium.copy(
                    color = Color.Green,
                    fontWeight = FontWeight.W800
                )
            )
        }

        if (itemCount.value == 0) {
            Button(onClick = { navController.navigate(Screen.Finish.route) }) {
                Text(text = "End your hunt")
            }
        } else {
            Button(onClick = {
                huntViewModel.setScore(reward)
                huntViewModel.incrementCorrectAnswers()
                predictionViewModel.resetPrediction()
                huntViewModel.pickNextItem()
                navController.navigate(Screen.Pending.route)
            }) {
                Text(text = "Next item")
            }
        }

        Spacer(modifier = Modifier.height(35.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun ItemValidationSuccessScreenPreview() {
    val navController = rememberNavController()
    val huntViewModel = HuntViewModel()
    val predictionViewModel = PredictionViewModel()
    HuntItemValidationSuccessScreen(navController, huntViewModel, predictionViewModel)
}