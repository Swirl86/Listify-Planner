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
import com.swirl.listifyplanner.R
import com.swirl.listifyplanner.presentation.MainViewModel
import com.swirl.listifyplanner.presentation.calendar_screen.CalendarScreen
import com.swirl.listifyplanner.presentation.calendar_screen.components.date.AddNoteToDateScreen
import com.swirl.listifyplanner.presentation.home_screen.HomeScreen
import com.swirl.listifyplanner.presentation.speech_to_text_screen.SpeechToTextScreen
import com.swirl.listifyplanner.presentation.todo_screen.TodoScreen
import com.swirl.listifyplanner.presentation.todo_screen.UpdateTodoScreen
import com.swirl.listifyplanner.utils.UiText
import com.swirl.listifyplanner.utils.extenstions.convertLocalDateToMillis
import com.swirl.listifyplanner.utils.extenstions.convertMillisToLocalDate
import java.time.LocalDate

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
                            Icon(
                                imageVector = navItem.icon,
                                contentDescription = UiText.StringResource(R.string.icon_arrow_back).asString()
                            )
                        },
                        label = {
                            Text(text = navItem.label)
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screens.HomeScreen.name,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(
                route = Screens.HomeScreen.name
            ) {
                HomeScreen()
            }
            composable(
                route = Screens.TodoScreen.name
            ) {
                TodoScreen(
                    mainViewModel = mainViewModel,
                    onUpdate = { id ->
                        navController.navigate(
                            route = "${Screens.UpdateTodoScreen.name}/$id"
                        )
                    })
            }
            composable(
                route = "${Screens.UpdateTodoScreen.name}/{id}",
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
                    UpdateTodoScreen(
                        id = id,
                        mainViewModel = mainViewModel,
                        onBack = {navController.popBackStack()}
                    )
                }
            }

            composable(route = Screens.SpeechToTextScreen.name) {
                SpeechToTextScreen(mainViewModel)
            }
            composable(route = Screens.CalendarScreen.name) {
                CalendarScreen(
                    mainViewModel = mainViewModel,
                    onAddNote = { date ->
                        navController.navigate(
                            route = "${Screens.AddNoteToDateScreen.name}/${date.convertLocalDateToMillis()}"
                        )
                    }
                )
            }
            composable(
                route = "${Screens.AddNoteToDateScreen.name}/{date}",
                arguments = listOf(navArgument("date") {
                    type = NavType.LongType
                    defaultValue = LocalDate.now().convertLocalDateToMillis()
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
                navBackStackEntry.arguments?.getLong("date")?.let { date ->
                    AddNoteToDateScreen(
                        mainViewModel = mainViewModel,
                        onBack = {navController.popBackStack()},
                        chosenDate = date.convertMillisToLocalDate()
                    )
                }
            }
        }
    }
}