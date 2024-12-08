package com.raj.chargepoint

import com.raj.chargepoint.data.FlowState
import com.raj.chargepoint.data.models.Schedule
import com.raj.chargepoint.domain.GetScheduleUseCase
import com.raj.chargepoint.screen.HomeUIState
import com.raj.chargepoint.screen.HomeViewModel
import io.mockk.*
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private val getScheduleUseCase = mockk<GetScheduleUseCase>()
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `homeUiState emits Success state`() = runTest {
        val schedules = listOf(Schedule("Charger_1", listOf("Truck_1", "Truck_2")))
        val flowState = flowOf(FlowState.Success(schedules))
        every {
            getScheduleUseCase(
                any(),
                any(),
                any()
            )
        } answers { flowState }
        viewModel = HomeViewModel(getScheduleUseCase)
        val results = viewModel.homeUiState.take(1).toList()

        assertEquals(1, results.size)
        assertEquals(HomeUIState.Success(schedules), results[0])

        verify { getScheduleUseCase(any(), any(), any()) }
    }

    @Test
    fun `homeUiState emits Error state`() = runTest {
        val exception = RuntimeException("Failed to generate schedule")
        val flowState = flowOf(FlowState.Failed(exception))
        every {
            getScheduleUseCase(
                any(),
                any(),
                any()
            )
        } answers { flowState }
        viewModel = HomeViewModel(getScheduleUseCase)
        val results = viewModel.homeUiState.take(1).toList()

        assertEquals(1, results.size)
        assertEquals(HomeUIState.Error(exception), results[0])

        verify { getScheduleUseCase(any(), any(), any()) }
    }

    @Test
    fun `homeUiState emits Loading state`() = runTest {

        val flowState = flowOf(FlowState.Loading)
        every {
            getScheduleUseCase(
                any(),
                any(),
                any()
            )
        } answers { flowState }

        viewModel = HomeViewModel(getScheduleUseCase)
        val results = viewModel.homeUiState.take(1).toList()

        assertEquals(1, results.size)
        assertEquals(HomeUIState.Loading, results[0])

        verify { getScheduleUseCase(any(), any(), any()) }
    }
}
