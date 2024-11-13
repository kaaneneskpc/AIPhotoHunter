package com.example.aiphotohunter.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.aiphotohunter.screens.PredictionViewModel
import com.example.aiphotohunter.screens.home.HomeScreen
import com.example.aiphotohunter.screens.home.HuntFinishScreen
import com.example.aiphotohunter.screens.hunt.HuntViewModel
import com.example.aiphotohunter.screens.hunt.item.HuntItemPendingScreen
import com.example.aiphotohunter.screens.hunt.item.HuntItemValidatingScreen
import com.example.aiphotohunter.screens.hunt.item.HuntItemValidationFailureScreen
import com.example.aiphotohunter.screens.hunt.item.HuntItemValidationSuccessScreen
import com.example.aiphotohunter.screens.hunt.view.HuntErrorScreen
import com.example.aiphotohunter.screens.hunt.view.HuntLoadingScreen
import com.example.aiphotohunter.screens.hunt.view.HuntPhotoLoadedScreen

@Composable
fun NavGraph(huntViewModel: HuntViewModel, predictionViewModel: PredictionViewModel) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) { HomeScreen(navController = navController, huntViewModel) }
        composable(Screen.Loading.route) { HuntLoadingScreen(navController = navController, huntViewModel = huntViewModel) }
        composable(Screen.Loaded.route) { HuntPhotoLoadedScreen(navController = navController, huntViewModel = huntViewModel) }
        composable(Screen.Pending.route) { HuntItemPendingScreen(navController = navController, huntViewModel = huntViewModel, predictionViewModel = predictionViewModel) }
        composable(Screen.Finish.route) { HuntFinishScreen(navController = navController, huntViewModel) }
        composable(Screen.ItemValidating.route) { HuntItemValidatingScreen(navController = navController, huntViewModel = huntViewModel, predictionViewModel = predictionViewModel) }
        composable(Screen.ItemValidationSuccess.route) { HuntItemValidationSuccessScreen(navController = navController, huntViewModel = huntViewModel, predictionViewModel = predictionViewModel) }
        composable(Screen.ItemValidationFailure.route) { HuntItemValidationFailureScreen(navController = navController, huntViewModel = huntViewModel, predictionViewModel = predictionViewModel) }
        composable(Screen.HuntError.route) { HuntErrorScreen(navController = navController, huntViewModel = huntViewModel) }
    }
}

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Loading : Screen("loading")
    data object Loaded : Screen("loaded")
    data object Pending : Screen("pending")
    data object Finish : Screen("finish")
    data object ItemValidating : Screen("itemValidating")
    data object ItemValidationSuccess : Screen("itemValidationSuccess")
    data object ItemValidationFailure : Screen("itemValidationFailure")
    data object HuntError : Screen("huntError")
}