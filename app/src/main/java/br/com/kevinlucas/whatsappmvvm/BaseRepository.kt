package br.com.kevinlucas.whatsappmvvm

import android.content.Context
import android.telephony.SmsManager

open class BaseRepository(context: Context) {

    fun isSendSMS(phone: String, message: String): Boolean {
        return try {
            val smsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(phone, null, message, null, null)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}