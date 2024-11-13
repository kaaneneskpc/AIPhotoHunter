package com.example.aiphotohunter.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.aiphotohunter.R
import com.example.aiphotohunter.components.LocationCard
import com.example.aiphotohunter.navigation.Screen
import com.example.aiphotohunter.screens.hunt.HuntViewModel

@Composable
fun HomeScreen(navController: NavHostController, huntViewModel: HuntViewModel) {
    val selectedButton = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(25.dp))
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Top Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Choose your hunting location",
            style = MaterialTheme.typography.titleLarge.copy(
                fontSize = 20.sp,
                fontWeight = MaterialTheme.typography.bodyLarge.fontWeight
            )
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            LocationCard(
                icon = "🏠",
                title = "  Home  ",
                selected = selectedButton.value == "Home",
                onTap = { selectedButton.value = "Home" },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(3.dp))
            LocationCard(
                icon = "🌳",
                title = " Outside",
                selected = selectedButton.value == "Outside",
                onTap = { selectedButton.value = "Outside" },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                huntViewModel.selectLocation(selectedButton.value.trim())
                navController.navigate(Screen.Loading.route)
            },
            enabled = selectedButton.value.isNotEmpty(),
            modifier = Modifier
                .padding(bottom = 32.dp)
        ) {
            Text(text = "Start Hunting")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val navController = rememberNavController()
    val huntViewModel = HuntViewModel()
    HomeScreen(navController, huntViewModel)
}