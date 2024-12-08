package com.raj.chargepoint.navigation

import androidx.navigation.NavController

class NavActions(navController: NavController) {

    val navigateToHome: () -> Unit = {
        navController.navigate(Destinations.HOME_SCREEN)

    }
}