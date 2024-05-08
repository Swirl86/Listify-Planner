package com.swirl.listifyplanner

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.swirl.listifyplanner.presentation.MainViewModel
import com.swirl.listifyplanner.presentation.navigation.AppNavigation
import com.swirl.listifyplanner.ui.theme.ListifyPlannerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var keepSplashOnScreen = true
        val delay = 2000L

        installSplashScreen().setKeepOnScreenCondition { keepSplashOnScreen }
        Handler(Looper.getMainLooper()).postDelayed({ keepSplashOnScreen = false }, delay)

        setContent {
            ListifyPlannerTheme {
                AppNavigation(mainViewModel = mainViewModel)
            }
        }
    }
}
