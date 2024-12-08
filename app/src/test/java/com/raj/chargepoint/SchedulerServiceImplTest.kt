package com.raj.chargepoint

import com.raj.chargepoint.data.SchedulerServiceImpl
import com.raj.chargepoint.data.models.Charger
import com.raj.chargepoint.data.models.Schedule
import com.raj.chargepoint.data.models.Truck
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class SchedulerServiceImplTest {

    private val schedulerService = SchedulerServiceImpl()

    @Test
    fun `test schedule with sufficient time for all trucks`() = runTest{
        val trucks = listOf(
            Truck("Truck_1", 50.0, 20.0),
            Truck("Truck_2", 60.0, 50.0),
            Truck("Truck_3", 70.0, 10.0),
            Truck("Truck_4", 80.0, 30.0)
        )
        val chargers = listOf(
            Charger("Charger_1", 10.0),
            Charger("Charger_2", 15.0)
        )
        val hours = 8

        val result = schedulerService.createSchedule(trucks, chargers, hours)

        val expected = listOf(
            Schedule("Charger_1", listOf("Truck_1", "Truck_2")),
            Schedule("Charger_2", listOf("Truck_3", "Truck_4"))
        )
        assertEquals(expected, result)
    }

    @Test
    fun `test schedule with insufficient time for some trucks`() = runTest{
        val trucks = listOf(
            Truck("Truck_1", 100.0, 10.0),
            Truck("Truck_2", 120.0, 40.0),
            Truck("Truck_3", 150.0, 30.0)
        )
        val chargers = listOf(
            Charger("Charger_1", 20.0)
        )
        val hours = 5

        val result = schedulerService.createSchedule(trucks, chargers, hours)

        val expected = listOf(
            Schedule("Charger_1", listOf("Truck_1"))
        )
        assertEquals(expected, result)
    }

    @Test
    fun `test schedule with no chargers`() = runTest{
        val trucks = listOf(
            Truck("Truck_1", 50.0, 20.0),
            Truck("Truck_2", 60.0, 50.0)
        )
        val chargers = emptyList<Charger>()
        val hours = 8

        val result = schedulerService.createSchedule(trucks, chargers, hours)

        val expected = emptyList<Schedule>()
        assertEquals(expected, result)
    }

    @Test
    fun `test schedule with no trucks`() = runTest{
        val trucks = emptyList<Truck>()
        val chargers = listOf(
            Charger("Charger_1", 10.0)
        )
        val hours = 8

        val result = schedulerService.createSchedule(trucks, chargers, hours)

        val expected = emptyList<Schedule>()
        assertEquals(expected, result)
    }

    @Test
    fun `test schedule with all trucks requiring more time than available`() = runTest{
        val trucks = listOf(
            Truck("Truck_1", 100.0, 0.0),
            Truck("Truck_2", 120.0, 0.0)
        )
        val chargers = listOf(
            Charger("Charger_1", 10.0)
        )
        val hours = 2

        val result = schedulerService.createSchedule(trucks, chargers, hours)

        val expected = emptyList<Schedule>()
        assertEquals(expected, result)
    }
}
