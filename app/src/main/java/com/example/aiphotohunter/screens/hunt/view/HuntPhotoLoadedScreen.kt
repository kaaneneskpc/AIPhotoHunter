package com.example.aiphotohunter.screens.hunt.view

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.aiphotohunter.navigation.HandleBackPressToHome
import com.example.aiphotohunter.navigation.Screen
import com.example.aiphotohunter.screens.hunt.HuntViewModel

@Composable
fun HuntPhotoLoadedScreen(navController: NavHostController, huntViewModel: HuntViewModel) {

    HandleBackPressToHome(navController, huntViewModel)

    val readyText by huntViewModel.readyText.collectAsState()
    val bringItOnText by huntViewModel.bringItOnText.collectAsState()
    val readyEmoji by huntViewModel.readyEmoji.collectAsState()

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
                text = readyEmoji, style = MaterialTheme.typography.displayLarge.copy(
                    color = MaterialTheme.colorScheme.onPrimary
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = readyText,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(50.dp))
        }

        Button(
            onClick = { navController.navigate(Screen.Pending.route) },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 35.dp)
        ) {
            Text(text = bringItOnText)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PhotoHuntLoadedScreenPreview() {
    val navController = rememberNavController()
    val huntViewModel = HuntViewModel()
    HuntPhotoLoadedScreen(navController, huntViewModel)
}