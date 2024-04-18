package com.swirl.listifyplanner.presentation.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.swirl.listifyplanner.presentation.MainViewModel
import com.swirl.listifyplanner.presentation.home_screen.HomeScreen
import com.swirl.listifyplanner.presentation.update_screen.UpdateScreen

@Composable
fun AppNavigation(mainViewModel: MainViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen.name
    ) {
        composable(
            route = Screen.HomeScreen.name
        ) {
            HomeScreen(
                mainViewModel = mainViewModel,
                onUpdate = { id ->
                    navController.navigate(
                        route = "${Screen.UpdateScreen.name}/$id"
                    )
                })
        }
        composable(
            route = "${Screen.UpdateScreen.name}/{id}",
            arguments = listOf(navArgument("id") {
                type = NavType.IntType
                defaultValue = -1
                nullable = false
            }),
            enterTransition = {
                slideInHorizontally (
                    initialOffsetX = {-it},
                    animationSpec = tween(300)
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = {-it},
                    animationSpec = tween(300)
                )
            }
        ) { navBackStackEntry ->
            navBackStackEntry.arguments?.getInt("id")?.let { id ->
                UpdateScreen(
                    id = id,
                    mainViewModel = mainViewModel,
                    onBack = {navController.popBackStack()}
                )
            }
        }
    }
}