package com.raj.chargepoint.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.raj.chargepoint.R
import com.raj.chargepoint.data.models.Schedule
import com.raj.chargepoint.ui.theme.chargepointTheme


@Composable
fun HomeScreen(innerPadding: PaddingValues) {
    val homeViewModel = hiltViewModel<HomeViewModel>()
    // collect the homeUiState from the view model
    val uiState by homeViewModel.homeUiState.collectAsStateWithLifecycle()
    ScheduleScreen(innerPadding, uiState)
}

@Composable
fun ScheduleScreen(innerPadding: PaddingValues, uiState: HomeUIState) {
    when (uiState) {
        is HomeUIState.Error -> Toast.makeText(
            LocalContext.current,
            stringResource(R.string.error_message, uiState.exception.message ?: ""),
            Toast.LENGTH_SHORT
        ).show()

        HomeUIState.Loading -> LoadingSpinner()

        is HomeUIState.Success -> {
            ScheduleList(innerPadding, uiState.data)

        }
    }
}

@Composable
fun ScheduleList(innerPadding: PaddingValues, schedules: List<Schedule>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        items(schedules) { schedule ->
            ScheduleItem(schedule)
        }
    }
}

@Composable
fun ScheduleItem(schedule: Schedule) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(
                    R.string.schedule_label,
                    schedule.chargerId,
                    schedule.truckIds.joinToString(", ")
                ),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}


@Composable
fun LoadingSpinner() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(
            modifier = Modifier.width(64.dp),
            color = MaterialTheme.colorScheme.secondary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )
    }
}

@Preview(showBackground = true, name = "Loading State")
@Composable
fun ScheduleScreenLoadingPreview() {
    chargepointTheme {
        ScheduleScreen(padding, HomeUIState.Loading)
    }
}

@Preview(showBackground = true, name = "Error State")
@Composable
fun ScheduleScreenErrorPreview() {
    chargepointTheme {
        ScheduleScreen(padding, HomeUIState.Error(Exception("")))
    }
}

val padding = PaddingValues(8.dp)