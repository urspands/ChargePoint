package com.raj.chargepoint.data

import com.raj.chargepoint.data.models.Charger
import com.raj.chargepoint.data.models.Schedule
import com.raj.chargepoint.data.models.Truck

interface SchedulerService {
    suspend fun createSchedule(
        trucks: List<Truck>,
        chargers: List<Charger>,
        hours: Int
    ): List<Schedule>
}