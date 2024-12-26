package com.varunkumar.expensetracker.sms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Telephony
import android.telephony.SmsMessage
import android.util.Log
import com.google.ai.client.generativeai.Chat
import com.google.mlkit.nl.entityextraction.Entity
import com.google.mlkit.nl.entityextraction.EntityExtraction
import com.google.mlkit.nl.entityextraction.EntityExtractionParams
import com.google.mlkit.nl.entityextraction.EntityExtractorOptions
import com.google.mlkit.nl.entityextraction.MoneyEntity
import com.varunkumar.expensetracker.data.Expense
import com.varunkumar.expensetracker.data.ExpenseType
import javax.inject.Inject

class SmsReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION == intent.action) {
            val bundle: Bundle? = intent.extras
            val format = bundle?.getString("format")
            val pdus = bundle?.get("pdus") as Array<*>?

            pdus?.forEach { pdu ->
                val message = SmsMessage.createFromPdu(pdu as ByteArray, format)
                val messageBody = message.messageBody
                val sender = message.originatingAddress

                // Parse the SMS body for transaction details
                extractEntitiesFromSms(
                    messageBody = messageBody,
                    onEntitiesExtracted = {
                        Log.d("extract info", "onReceive: $it")
                    }
                )
            }
        }
    }

    private fun extractEntitiesFromSms(
        messageBody: String,
        onEntitiesExtracted: (Expense) -> Unit
    ) {
        val entityExtractor = EntityExtraction.getClient(
            EntityExtractorOptions.Builder(EntityExtractorOptions.ENGLISH).build()
        )

        val params = EntityExtractionParams.Builder(messageBody).build()

        entityExtractor
            .downloadModelIfNeeded()
            .addOnSuccessListener {
                entityExtractor.annotate(params)
                    .addOnSuccessListener { entityAnnotations ->
                        var extractedAmount = 0.0
                        var extractedName = ""

                        for (entityAnnotation in entityAnnotations) {
                            for (entity in entityAnnotation.entities) {
                                when (entity.type) {
                                    Entity.TYPE_MONEY -> {
                                        val moneyEntity = entity as MoneyEntity
                                        extractedAmount = moneyEntity.integerPart.toDouble()
                                    }
                                }
                            }
                        }

                        // Manually extract the name from the SMS body, e.g., "HungerBox"
                        extractedName = extractTransactionName(messageBody)

                        // Create an Expense object
                        val expense = Expense(
                            name = extractedName,
                            amount = extractedAmount,
                            expenseType = ExpenseType.FOOD // Assuming the transaction type is FOOD
                        )

                        onEntitiesExtracted(expense)
                    }
                    .addOnFailureListener { e ->
                        Log.e("SmsReceiver", "Entity extraction failed: ${e.message}")
                    }
            }
            .addOnFailureListener { e ->
                Log.e("SmsReceiver", "Model download failed: ${e.message}")
            }
    }

    private fun extractTransactionName(messageBody: String): String {
        // This is a simple example of extracting the recipient name manually from the SMS body
        val nameRegex = Regex("trf to ([A-Za-z]+)")
        val matchResult = nameRegex.find(messageBody)
        return matchResult?.groupValues?.get(1) ?: "Unknown"
    }
}