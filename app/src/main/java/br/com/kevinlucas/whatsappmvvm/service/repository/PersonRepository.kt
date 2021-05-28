package br.com.kevinlucas.whatsappmvvm.service.repository

import android.content.Context

class PersonRepository(context: Context) : BaseRepository(context) {

    fun login(phone: String, message: String) : Boolean {
        return isSendSMS(phone, message)
    }

}