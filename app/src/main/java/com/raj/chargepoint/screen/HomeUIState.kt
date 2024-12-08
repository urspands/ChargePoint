package com.raj.chargepoint.screen

import com.raj.chargepoint.data.models.Schedule

sealed class HomeUIState {
    data object Loading : HomeUIState()
    data class Error(val exception: Exception) : HomeUIState()
    data class Success(val data: List<Schedule>) : HomeUIState()
}
