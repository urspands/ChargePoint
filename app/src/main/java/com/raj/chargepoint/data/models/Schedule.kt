package com.raj.chargepoint.data.models

data class Schedule(
    val chargerId: String,
    val truckIds: List<String>
)