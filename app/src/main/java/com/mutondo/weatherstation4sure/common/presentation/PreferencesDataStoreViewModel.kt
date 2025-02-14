package com.mutondo.weatherstation4sure.common.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mutondo.weatherstation4sure.common.data.repository.PreferencesDataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PreferencesDataStoreViewModel @Inject constructor(
    private val repository: PreferencesDataStoreRepository
) : ViewModel() {

    fun onEvent(event: PreferencesDataStoreEvent) {
        when(event) {
            is PreferencesDataStoreEvent.UpdateShowCelsius -> {
                updateShowCelsius(
                    event.showCelsius
                )
            }
        }
    }

    val readPreferencesFromDataStore = repository.readFromDataStore

    private fun updateShowCelsius(show: Boolean) {
        viewModelScope.launch {
            repository.updateShowCelsius(show)
        }
    }
}
