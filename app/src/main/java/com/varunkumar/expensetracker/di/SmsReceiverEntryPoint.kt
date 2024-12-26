package com.varunkumar.expensetracker.di

import com.google.ai.client.generativeai.Chat
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface SmsReceiverEntryPoint {
    fun chat(): Chat
}