package com.mutondo.weatherstation4sure.common.presentation

sealed class PreferencesDataStoreEvent {
    data class UpdateShowCelsius(
        val showCelsius: Boolean
    ) : PreferencesDataStoreEvent()
}
