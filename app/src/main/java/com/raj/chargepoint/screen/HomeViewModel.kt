package com.raj.chargepoint.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raj.chargepoint.data.FlowState
import com.raj.chargepoint.data.models.Charger
import com.raj.chargepoint.data.models.Truck
import com.raj.chargepoint.domain.GetScheduleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(getScheduleUseCase: GetScheduleUseCase) : ViewModel() {
    //Truck list and charger list can be updated from the UI and this implementation is just for demo purpose
    private val truckList = MutableStateFlow<List<Truck>>(getTrucks())
    private val chargerList = MutableStateFlow<List<Charger>>(getChargers())

    @OptIn(ExperimentalCoroutinesApi::class)
    val homeUiState = combine(truckList, chargerList) { trucks, chargers ->
        getScheduleUseCase(trucks, chargers, TOTAL_HOURS)
    }.flatMapLatest { flowState ->
            flowState.mapLatest { state ->
                when (state) {
                    is FlowState.Failed -> HomeUIState.Error(state.exception)
                    FlowState.Loading -> HomeUIState.Loading
                    is FlowState.Success -> HomeUIState.Success(state.data)
                }
            }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), HomeUIState.Loading)

    fun getTrucks(): List<Truck> = listOf(
        Truck("Truck_1", 50.0, 20.0),
        Truck("Truck_4", 20.0, 30.0),
        Truck("Truck_3", 70.0, 10.0),
        Truck("Truck_2", 90.0, 50.0),
        Truck("Truck_5", 70.0, 10.0),
        Truck("Truck_6", 90.0, 50.0)
    )

    fun getChargers(): List<Charger> = listOf(
        Charger("Charger_1", 10.0),
        Charger("Charger_2", 15.0),
        Charger("Charger_3", 20.0)
    )

    companion object {
        const val TOTAL_HOURS = 8
    }
}