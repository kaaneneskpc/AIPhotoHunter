package com.example.aiphotohunter.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.aiphotohunter.screens.hunt.HuntViewModel

@Composable
fun HandleBackPressToHome(navController: NavHostController, huntViewModel: HuntViewModel) {
    BackHandler {
        //huntViewModel.reset()
        navController.popBackStack(Screen.Home.route, inclusive = false)
    }
}