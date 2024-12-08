package com.raj.chargepoint.domain

import com.raj.chargepoint.data.models.Charger
import com.raj.chargepoint.data.models.Truck
import javax.inject.Inject

class GetScheduleUseCase @Inject constructor(private val schedulerRepository: SchedulerRepository) {
    operator fun invoke(trucks: List<Truck>, chargers: List<Charger>, hours: Int) =
        schedulerRepository.getTrucksChargingSchedule(trucks, chargers, hours)
}