package com.mutondo.weatherstation4sure.common.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.mutondo.weatherstation4sure.utils.Constants.SHOW_CELSIUS
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

data class IotPreference(
    val showCelsius: Boolean
)

class PreferencesDataStoreRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    private object PreferencesKeys {
        val SHOW_CELSIUS = booleanPreferencesKey("show_celsius")
    }

    val readFromDataStore = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            IotPreference(
                showCelsius = preferences[PreferencesKeys.SHOW_CELSIUS] ?: SHOW_CELSIUS,
            )
        }

    suspend fun updateShowCelsius(showCelsius: Boolean) {
        dataStore.edit {
            it[PreferencesKeys.SHOW_CELSIUS] = showCelsius
        }
    }
}