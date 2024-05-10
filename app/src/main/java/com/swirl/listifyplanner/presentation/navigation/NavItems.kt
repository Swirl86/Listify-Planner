package com.swirl.listifyplanner.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Mic
import androidx.compose.ui.graphics.vector.ImageVector

data class NavItem (
    val label: String,
    val icon: ImageVector,
    val route: String
)

val listOfBottomNavItems = listOf(
    NavItem(
        label = "Home",
        icon = Icons.Default.Home,
        route = Screens.HomeScreen.name
    ),
    NavItem(
        label = "Todo",
        icon = Icons.Default.EditNote,
        route = Screens.TodoScreen.name
    ),
    NavItem(
        label = "Talk",
        icon = Icons.Default.Mic,
        route = Screens.SpeechToTextScreen.name
    ),
    NavItem(
        label = "Calendar",
        icon = Icons.Default.CalendarMonth,
        route = Screens.CalendarScreen.name
    )
)