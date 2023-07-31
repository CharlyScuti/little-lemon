package com.tr3sco.littlelemon.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun MyNavigation(navHostController: NavHostController) {
    val destination = if (LocalContext.current.hasUserData) Home.route else Onboarding.route
    NavHost(navController = navHostController, startDestination = destination) {
        composable(Onboarding.route) {
            Onboarding(navHostController)
        }
        composable(Home.route) {
            Home(navHostController)
        }
        composable(Profile.route) {
            Profile(navHostController)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MyNavigationPreview() {
    MyNavigation(rememberNavController())
}