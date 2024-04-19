package com.swirl.listifyplanner.presentation.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.swirl.listifyplanner.presentation.MainViewModel
import com.swirl.listifyplanner.presentation.home_screen.HomeScreen
import com.swirl.listifyplanner.presentation.update_screen.UpdateScreen
import com.swirl.listifyplanner.presentation.speech_to_text_screen.SpeechToTextScreen

@Composable
fun AppNavigation(mainViewModel: MainViewModel) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                listOfBottomNavItems.forEach { navItem ->
                    NavigationBarItem(
                        selected = currentDestination?.hierarchy?.any { it.route == navItem.route } == true,
                        onClick = {
                            navController.navigate(navItem.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(imageVector = navItem.icon, contentDescription = null)
                        },
                        label = {
                            Text(text = navItem.label)
                        }
                    )
                }
            }
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = Screens.HomeScreen.name,
            modifier = Modifier.padding(it)
        ) {
            composable(
                route = Screens.HomeScreen.name
            ) {
                HomeScreen(
                    mainViewModel = mainViewModel,
                    onUpdate = { id ->
                        navController.navigate(
                            route = "${Screens.UpdateScreen.name}/$id"
                        )
                    })
            }
            composable(
                route = "${Screens.UpdateScreen.name}/{id}",
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
            composable(route = Screens.SpeechToTextScreen.name) {
                SpeechToTextScreen()
            }
            composable(route = Screens.CalenderScreen.name) {
                // TODO implement calender
                SpeechToTextScreen()
            }
        }
    }
}