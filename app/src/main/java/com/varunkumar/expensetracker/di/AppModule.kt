package com.varunkumar.expensetracker.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.varunkumar.expensetracker.DataStoreRepository
import com.varunkumar.expensetracker.biometrics.BiometricAuthentication
import com.varunkumar.expensetracker.data.dao.ExpenseDao
import com.varunkumar.expensetracker.data.database.ExpenseDatabase
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
    fun provideDatabase(@ApplicationContext context: Context): ExpenseDatabase {
        return Room.databaseBuilder(
            context,
            ExpenseDatabase::class.java,
            "expense_db"
        ).build()
    }

    @Provides
    fun provideExpenseDao(database: ExpenseDatabase): ExpenseDao {
        return database.expenseDao()
    }

    @Provides
    @Singleton
    fun provideBiometricAuthenticator(@ApplicationContext context: Context) : BiometricAuthentication {
        return BiometricAuthentication(context)
    }

}