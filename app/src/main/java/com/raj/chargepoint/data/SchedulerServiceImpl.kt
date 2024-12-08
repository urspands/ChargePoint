package com.raj.chargepoint.data

import com.raj.chargepoint.data.models.Charger
import com.raj.chargepoint.data.models.Schedule
import com.raj.chargepoint.data.models.Truck

/**
 * SchedulerServiceImpl is responsible for creating a charging schedule for electric trucks
 * based on the available chargers, truck requirements, and a specified time window.
 *
 * The algorithm ensures that:
 * 1. Chargers are assigned to trucks in a first-come-first-serve manner based on truck's arrival time for charging station.
 * 2. Chargers can only charge one truck at a time.
 * 3. Trucks that cannot be fully charged within the specified time window are skipped.
 **/

class SchedulerServiceImpl() : SchedulerService {
    /**
     * Creates a charging schedule for the given list of trucks and chargers within a specified time window.
     * @param trucks List of [Truck]
     * @param chargers List of [Charger]
     * @param hours Total time window in hours
     */
    override suspend fun createSchedule(
        trucks: List<Truck>,
        chargers: List<Charger>,
        hours: Int
    ): List<Schedule> {
        if (trucks.isEmpty() || chargers.isEmpty()) return emptyList()

        val schedules = mutableListOf<Schedule>()
        val availableChargers = chargers.toMutableList()
        //sort the truck list by arrivalTimeStamp for FirstComeFirstServe
        val trucksQueue = trucks.sortedBy { it.arrivalTimeStamp }.toMutableList()

        while (availableChargers.isNotEmpty() && trucksQueue.isNotEmpty()) {
            val charger = availableChargers.removeAt(0)
            val chargerSchedule = mutableListOf<String>()
            var timeRemaining = hours.toDouble()

            // Iterator to safely modify the list while iterating
            val truckQueueIterator = trucksQueue.iterator()
            while (truckQueueIterator.hasNext()) {
                val truck = truckQueueIterator.next()
                val requiredCharge =
                    (truck.batteryCapacityKWh * (100.0 - truck.currentChargePercentage)) / 100.0
                val chargingTime = requiredCharge / charger.kWChargingRate

                if (chargingTime <= timeRemaining) {
                    // Add the truck to this current charger schedule
                    chargerSchedule.add(truck.id)
                    // Safely remove the truck from the queue
                    truckQueueIterator.remove()
                    timeRemaining -= chargingTime
                }
            }
            if (chargerSchedule.isNotEmpty()) {
                schedules.add(Schedule(charger.id, chargerSchedule))
            }
        }
        return schedules
    }
}