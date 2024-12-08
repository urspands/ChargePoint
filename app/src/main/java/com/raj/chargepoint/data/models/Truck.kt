package com.raj.chargepoint.data.models

data class Truck(
    val id: String,
    val batteryCapacityKWh: Double,
    var currentChargePercentage: Double,
    val arrivalTimeStamp: Long = System.currentTimeMillis()
)