package com.varunkumar.expensetracker.di

import android.content.Context
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.FragmentActivity
import com.varunkumar.expensetracker.biometrics.BiometricAuthentication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.android.scopes.ViewScoped
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
class ViewModelModule {
    @Provides
    @ViewModelScoped
    fun provideBiometricAuthenticator(@ApplicationContext context: Context): BiometricAuthentication {
        return BiometricAuthentication(context)
    }

    @Provides
    @ViewModelScoped
    fun provideFragmentActivity(@ApplicationContext context: Context): FragmentActivity {
        return context as FragmentActivity
    }
}