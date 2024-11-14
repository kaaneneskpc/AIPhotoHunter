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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController, huntViewModel: HuntViewModel) {
    val selectedButton = remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    val languages = huntViewModel.languages
    val languageLabel by huntViewModel.languageLabel.collectAsState()
    val selectLanguagePlaceholder by huntViewModel.selectLanguagePlaceholder.collectAsState()
    val selectedLanguage by huntViewModel.selectedLanguage.collectAsState()
    val isLoading by huntViewModel.isLoading.collectAsState()
    val locationPrompt by huntViewModel.locationPrompt.collectAsState()
    val homeTitle by huntViewModel.homeTitle.collectAsState()
    val outsideTitle by huntViewModel.outsideTitle.collectAsState()
    val startHuntingButtonText by huntViewModel.startHuntingButtonText.collectAsState()
    val logoDescription by huntViewModel.logoDescription.collectAsState()

    if (isLoading) {
        Spacer(modifier = Modifier.height(50.dp))
        CircularProgressIndicator()
        Spacer(modifier = Modifier.height(50.dp))
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(25.dp))
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = selectedLanguage ?: selectLanguagePlaceholder,
                    onValueChange = { /* No-op since it's read-only */ },
                    readOnly = true,
                    label = { Text(languageLabel) },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    languages.forEach { language ->
                        DropdownMenuItem(
                            text = { Text(text = language) },
                            onClick = {
                                huntViewModel.setSelectedLanguage(language)
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(25.dp))
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = logoDescription,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = locationPrompt,
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
                    title = homeTitle,
                    selected = selectedButton.value == "Home",
                    onTap = { selectedButton.value = "Home" },
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(3.dp))
                LocationCard(
                    icon = "🌳",
                    title = outsideTitle,
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
                Text(text = startHuntingButtonText)
            }
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