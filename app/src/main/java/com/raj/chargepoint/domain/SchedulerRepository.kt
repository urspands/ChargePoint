package com.raj.chargepoint.domain

import com.raj.chargepoint.data.FlowState
import com.raj.chargepoint.data.models.Charger
import com.raj.chargepoint.data.models.Schedule
import com.raj.chargepoint.data.models.Truck
import kotlinx.coroutines.flow.Flow

interface SchedulerRepository {
    fun getTrucksChargingSchedule(trucks: List<Truck>, chargers: List<Charger>, hours: Int): Flow<FlowState<List<Schedule>>>
}