package com.example.autovault.ViewModel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.autovault.data.car_api.GetVehicleData
import kotlinx.coroutines.launch
import androidx.compose.runtime.State

@RequiresApi(Build.VERSION_CODES.P)
class QuickFixHelpViewModel(
    private val getVehicleData: GetVehicleData
) : ViewModel() {
    private val _state = mutableStateOf(ViewModelState())
    val state: State<ViewModelState> = _state

    init {
        observerVehicleStatus()
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun observerVehicleStatus() {
        viewModelScope.launch {
            getVehicleData.fetchData()
                .collect { status ->
//                    withContext(Dispatchers.IO) {
//                        checkForAlert.checkAndNotify(status)
//                        postIfChanged(status)
//                    }

                    _state.value = ViewModelState(status = status)

                }
        }
    }
}