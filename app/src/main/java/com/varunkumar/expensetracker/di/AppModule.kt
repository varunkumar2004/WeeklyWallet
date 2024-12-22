package com.varunkumar.expensetracker.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.varunkumar.expensetracker.DataStoreRepository
import com.varunkumar.expensetracker.biometrics.BiometricAuthentication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

val Context.dataStore : DataStore<Preferences> by preferencesDataStore("expenseDatastore")

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideDataStoreRepository(@ApplicationContext context: Context): DataStoreRepository {
        return DataStoreRepository(context.dataStore)
    }

    @Provides
    @Singleton
    fun provideBiometricAuthenticator(@ApplicationContext context: Context) : BiometricAuthentication {
        return BiometricAuthentication(context)
    }

}