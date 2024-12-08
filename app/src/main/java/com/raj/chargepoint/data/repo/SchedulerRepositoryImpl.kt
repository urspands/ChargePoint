package com.raj.chargepoint.data.repo

import com.raj.chargepoint.data.FlowState
import com.raj.chargepoint.data.SchedulerService
import com.raj.chargepoint.data.models.Charger
import com.raj.chargepoint.data.models.Schedule
import com.raj.chargepoint.data.models.Truck
import com.raj.chargepoint.domain.SchedulerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SchedulerRepositoryImpl @Inject constructor(private val schedulerService: SchedulerService) :
    SchedulerRepository {
    override fun getTrucksChargingSchedule(
        trucks: List<Truck>,
        chargers: List<Charger>,
        hours: Int
    ): Flow<FlowState<List<Schedule>>> = flow {
        emit(FlowState.Loading)
        emit(getSchedule(trucks, chargers, hours))
    }.flowOn(Dispatchers.Default)

    private suspend fun getSchedule(
        trucks: List<Truck>,
        chargers: List<Charger>,
        hours: Int
    ): FlowState<List<Schedule>> {
        return try {
            FlowState.Success(schedulerService.createSchedule(trucks, chargers, hours))
        } catch (exception: Exception) {
            FlowState.Failed(exception)
        }
    }
}