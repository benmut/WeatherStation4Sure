package com.mutondo.weatherstation4sure.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val IOT_PREFERENCES_NAME = "iot_preferences"
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(IOT_PREFERENCES_NAME)

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideIotDataStorePreferences(@ApplicationContext applicationContext: Context): DataStore<Preferences> {
        return applicationContext.dataStore
    }
}