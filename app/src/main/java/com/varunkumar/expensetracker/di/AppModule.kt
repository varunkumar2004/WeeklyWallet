package com.varunkumar.expensetracker.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.google.ai.client.generativeai.Chat
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.google.ai.client.generativeai.type.generationConfig
import com.varunkumar.expensetracker.BuildConfig
import com.varunkumar.expensetracker.data.DataStoreRepository
import com.varunkumar.expensetracker.biometrics.BiometricAuthentication
import com.varunkumar.expensetracker.data.dao.ExpenseDao
import com.varunkumar.expensetracker.data.database.ExpenseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("expenseDatastore")

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
    @Singleton
    fun provideExpenseDao(database: ExpenseDatabase): ExpenseDao {
        return database.expenseDao()
    }

    @Provides
    @Singleton
    fun provideGenerativeModel(): GenerativeModel {
        val model = GenerativeModel(
            modelName = "gemini-1.5-flash",
            BuildConfig.apiKey,
            generationConfig = generationConfig {
                temperature = 1f
                topK = 40
                topP = 0.95f
                maxOutputTokens = 8192
            }
        )

        return model
    }

    @Provides
    @Singleton
    fun provideModelChat(model: GenerativeModel): Chat {
        val chatHistory = listOf(
            content("user") {
                text("\"Dear UPI user A/C X2342 debited by 10.0 on date 24Oct24 trf to MITHUN LAL Refno 466490419269. If not u? call 1800111109. -SBI\" always all these sms into expense data class json format which is data class Expense(val name: String,val amount: Double,val expenseType: ExpenseType) and ExpenseType is an enum class which enum class ExpenseType {ENTERTAINMENT,FINANCE,FOOD,TRANSPORT,GROCERIES,OTHER} just give me json format not any other things whatsoever\n")
            },
            content("model") {
                text("```json\n{\n  \"name\": \"MITHUN LAL\",\n  \"amount\": 10.0,\n  \"expenseType\": \"OTHER\"\n}\n```\n")
            },
            content("user") {
                text("what will be json format for this \"HungerBox has requested Rs50 frm u on Google Pay app. Once approved, money will be debited frm ur a/c -SBI\"")
            },
            content("model") {
                text("```json\n{\n  \"name\": \"HungerBox\",\n  \"amount\": 50.0,\n  \"expenseType\": \"FOOD\"\n}\n```\n")
            },
            content("user") {
                text("Dear UPI user A/C X2342 debited by 270.0 on date 31Oct24 trf to Swiggy Limited Refno 430565747271. If not u? call 1800111109. -SBI")
            },
            content("model") {
                text("```json\n{\n  \"name\": \"Swiggy Limited\",\n  \"amount\": 270.0,\n  \"expenseType\": \"FOOD\"\n}\n```\n")
            },
            content("user") {
                text("UPI-Mandate successfully Revoked towards Spotify India for Rs59.00 -SBI")
            },
            content("model") {
                text("{\n  \"name\": \"Spotify India\",\n  \"amount\": 59.00,\n  \"expenseType\": \"ENTERTAINMENT\"\n}")
            },
        )

        return model.startChat(chatHistory)
    }
}