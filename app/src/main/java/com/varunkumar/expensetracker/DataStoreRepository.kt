package com.varunkumar.expensetracker

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreRepository (
    private val dataStore: DataStore<Preferences>
) {
    companion object {
        val DAILY_LIMIT = intPreferencesKey("daily_limit")
    }

    val dailyLimitFlow: Flow<Int> = dataStore.data
        .map { preferences ->
            preferences[DAILY_LIMIT] ?: 500
        }

    suspend fun updateDailyLimit(limit: Int) {
        dataStore.edit { preferences ->
            preferences[DAILY_LIMIT] = limit
        }
    }
}