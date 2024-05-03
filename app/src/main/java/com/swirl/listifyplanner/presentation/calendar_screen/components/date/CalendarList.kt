package com.swirl.listifyplanner.presentation.calendar_screen.components.date

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import com.swirl.listifyplanner.presentation.common.GoToTop
import com.swirl.listifyplanner.utils.extenstions.isScrollingUp
import com.swirl.listifyplanner.utils.extenstions.isoWeekNumber
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun CalendarList() {
    val scope = rememberCoroutineScope()
    val lazyListState = rememberLazyListState()
    val lastWeekShown = remember { mutableIntStateOf(-1) }
    val startDate = LocalDate.now()
    val totalDays = remember { mutableIntStateOf(30) }

    LaunchedEffect(lazyListState) {
        snapshotFlow { lazyListState.layoutInfo.visibleItemsInfo }
            .collect { visibleItems ->
                val lastVisibleIndex = visibleItems.lastOrNull()?.index ?: return@collect
                if (lastVisibleIndex >= totalDays.intValue - 10) {
                    totalDays.intValue += 10
                }
            }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = lazyListState
    ) {

        items(totalDays.intValue) { index ->
            key(index) {
                val date = startDate.plusDays(index.toLong())

                val monthTitle = if (date.dayOfMonth == 1 || date == startDate)
                    "${date.month.getDisplayName(TextStyle.FULL_STANDALONE, Locale.getDefault())} ${date.year} " else ""

                if (monthTitle.isNotEmpty()) {
                    MonthHeader(monthTitle)
                }

                val currentWeek = date.isoWeekNumber
                if (currentWeek != lastWeekShown.intValue) {
                    WeekNumberHeader(currentWeek)
                    lastWeekShown.intValue = currentWeek
                }
                DayCard(date)
            }
        }
    }

    AnimatedVisibility(
        visible = !lazyListState.isScrollingUp(),
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        GoToTop {
            scope.launch { lazyListState.scrollToItem(0) }
        }
    }
}

