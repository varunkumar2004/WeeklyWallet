package com.varunkumar.expensetracker

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Telephony
import android.telephony.SmsMessage
import android.util.Log

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
                parseTransactionMessage(messageBody)
            }
        }
    }

    private fun isBankOrPaymentMessage(sender: String): Boolean {
        // Check if the message is from a bank or payment service (adjust the sender criteria)
        return sender.contains("BANK") || sender.contains("PAY")
    }

    private fun parseTransactionMessage(messageBody: String) {
        // Custom logic to extract transaction details like amount, date, etc.
        // e.g., extract "Rs 1000" from "You've spent Rs 1000 at XYZ."
        Log.d("SmsReceiver", "Transaction Message: $messageBody")
        // Store the transaction details to your expenses database
    }
}