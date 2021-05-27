package br.com.kevinlucas.whatsappmvvm

import android.content.Context

class PersonRepository(val context: Context) : BaseRepository(context) {

    fun login(phone: String, message: String) : Boolean {
        return isSendSMS(phone, message)
    }

}