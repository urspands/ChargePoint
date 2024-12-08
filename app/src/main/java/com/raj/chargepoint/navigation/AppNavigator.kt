package com.raj.chargepoint.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.raj.chargepoint.screen.HomeScreen


@Composable
fun AppNavigator(innerPaddingValues: PaddingValues) {
    val navController = rememberNavController()
    // navActions can be passed to screens to navigate to different destinations
    val navActions = remember(navController) { NavActions(navController) }
    NavHost(navController, startDestination = Destinations.HOME_SCREEN) {
        composable(Destinations.HOME_SCREEN) {
            HomeScreen(innerPaddingValues)
        }
    }
}