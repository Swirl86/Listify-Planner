package com.swirl.listifyplanner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.swirl.listifyplanner.db.AppDatabase
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
