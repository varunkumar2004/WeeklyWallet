package com.varunkumar.expensetracker

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Telephony
import android.telephony.SmsMessage
import android.util.Log
import com.google.mlkit.nl.entityextraction.EntityExtraction
import com.google.mlkit.nl.entityextraction.EntityExtractionParams
import com.google.mlkit.nl.entityextraction.EntityExtractorOptions
import com.varunkumar.expensetracker.data.dao.ExpenseDao
import javax.inject.Inject

//import com.google.mlkit.nl.entityextraction.*

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
        onEntitiesExtracted: (List<String>) -> Unit
    ) {
        val entityExtractor =
            EntityExtraction.getClient(
                EntityExtractorOptions.Builder(EntityExtractorOptions.ENGLISH)
                    .build()
            )

        val params = EntityExtractionParams.Builder(messageBody).build()

        entityExtractor
            .downloadModelIfNeeded()
            .addOnSuccessListener {
                entityExtractor
                    .annotate(params)
                    .addOnSuccessListener {
                        val extractedInfo = mutableListOf<String>()
                        onEntitiesExtracted(extractedInfo)
                    }
            }
    }

//    private fun extractEntitiesFromSms(messageBody: String, onEntitiesExtracted: (List<String>) -> Unit) {
//        val entityExtractor = EntityExtraction.getClient(EntityExtractorOptions.Builder(EntityExtractorOptions.ENGLISH).build())
//
//        // Prepare the input
//        val params = EntityExtractionParams.Builder(messageBody).build()
//
//        // Extract entities
//        entityExtractor.downloadModelIfNeeded()
//            .addOnSuccessListener {
//                entityExtractor.annotate(params)
//                    .addOnSuccessListener { entityAnnotations ->
//                        val extractedInfo = mutableListOf<String>()
//
//                        // Loop through detected entities
//                        for (annotation in entityAnnotations) {
//                            for (entity in annotation.entities) {
//                                when (entity.type) {
//                                    Entity.TYPE_MONEY -> {
//                                        val amount = (entity as MoneyEntity).unnormalizedCurrency
//                                        extractedInfo.add("Amount: $amount")
//                                    }
//                                    Entity.TYPE_DATE_TIME -> {
//                                        val date = (entity as DateTimeEntity).timestampMillis
//                                        extractedInfo.add("Date: $date")
//                                    }
//                                    // Add more cases as needed for different entity types
//                                }
//                            }
//                        }
//
//                        // Return extracted info
//                        onEntitiesExtracted(extractedInfo)
//                    }
//                    .addOnFailureListener { e ->
//                        Log.e("EntityExtraction", "Failed to extract entities", e)
//                    }
//            }
//    }

}