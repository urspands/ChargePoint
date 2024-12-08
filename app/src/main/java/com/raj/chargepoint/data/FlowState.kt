package com.raj.chargepoint.data

sealed class FlowState<out T> {
    object Loading : FlowState<Nothing>()
    data class Success<T>(val data: T) : FlowState<T>()
    data class Failed(val exception: Exception) : FlowState<Nothing>()
}