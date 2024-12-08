package com.raj.chargepoint

import com.raj.chargepoint.data.FlowState
import com.raj.chargepoint.data.SchedulerService
import com.raj.chargepoint.data.models.Charger
import com.raj.chargepoint.data.models.Schedule
import com.raj.chargepoint.data.models.Truck
import com.raj.chargepoint.data.repo.SchedulerRepositoryImpl
import io.mockk.*
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Test


class SchedulerRepositoryImplTest {
    // Mock SchedulerService
    private val schedulerService = mockk<SchedulerService>()
    private val schedulerRepository = SchedulerRepositoryImpl(schedulerService)

    @Test
    fun `getTrucksChargingSchedule emits Loading and Success states`() = runTest {

        val trucks = listOf(Truck("Truck_1", 50.0, 20.0))
        val chargers = listOf(Charger("Charger_1", 10.0))
        val hours = 8

        val expectedSchedules = listOf(Schedule("Charger_1", listOf("Truck_1")))
        coEvery {
            schedulerService.createSchedule(
                any(),
                any(),
                any()
            )
        } returns expectedSchedules

        val flowResults = mutableListOf<FlowState<List<Schedule>>>()
        schedulerRepository.getTrucksChargingSchedule(trucks, chargers, hours)
            .toList(flowResults)

        // Verify emitted states
        assertEquals(2, flowResults.size)
        assertEquals(FlowState.Loading, flowResults[0])
        assertEquals(FlowState.Success(expectedSchedules), flowResults[1])

        // Verify interaction with the service
        coVerify { schedulerService.createSchedule(trucks, chargers, hours) }
    }

    @Test
    fun `getTrucksChargingSchedule emits Loading and Failed states on error`() = runBlocking {

        val trucks = listOf(Truck("Truck_1", 50.0, 20.0))
        val chargers = listOf(Charger("Charger_1", 10.0))
        val hours = 8

        val exception = RuntimeException("Failed to create schedule")
        coEvery { schedulerService.createSchedule(any(), any(), any()) } throws exception

        val flowResults = mutableListOf<FlowState<List<Schedule>>>()
        schedulerRepository.getTrucksChargingSchedule(trucks, chargers, hours)
            .toList(flowResults)

        // Verify emitted states
        assertEquals(2, flowResults.size)
        assertEquals(FlowState.Loading, flowResults[0])
        assertEquals(FlowState.Failed(exception), flowResults[1])

        // Verify interaction with the service
        coVerify { schedulerService.createSchedule(trucks, chargers, hours) }
    }
}
