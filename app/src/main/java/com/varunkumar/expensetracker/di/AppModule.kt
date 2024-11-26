package com.varunkumar.expensetracker.di

import android.content.Context
import com.varunkumar.expensetracker.biometrics.BiometricAuthentication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideBiometricAuthenticator(@ApplicationContext context: Context) : BiometricAuthentication {
        return BiometricAuthentication(context)
    }
}