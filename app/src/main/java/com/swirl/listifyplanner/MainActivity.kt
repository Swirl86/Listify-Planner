package com.swirl.listifyplanner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.swirl.listifyplanner.presentation.MainViewModel
import com.swirl.listifyplanner.presentation.navigation.AppNavigation
import com.swirl.listifyplanner.ui.theme.ListifyPlannerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ListifyPlannerTheme {
                AppNavigation(mainViewModel = mainViewModel)
            }
        }
    }
}
