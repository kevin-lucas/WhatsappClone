package br.com.kevinlucas.whatsappmvvm.service.repository

import android.content.Context
import br.com.kevinlucas.whatsappmvvm.service.constants.WhatsappConstants
import br.com.kevinlucas.whatsappmvvm.service.model.MessageModel
import br.com.kevinlucas.whatsappmvvm.service.repository.local.SecurityPreferences
import br.com.kevinlucas.whatsappmvvm.service.repository.remote.FirebaseClient

class ChatRepository(context: Context) : BaseRepository(context) {

    private val mSharedPreferences = SecurityPreferences(context)


    fun sendMessage(contactId: String, message: String) {

        val idSender = mSharedPreferences.get(WhatsappConstants.SHARED.PERSON_KEY)
        val idRecipient = contactId
        val messageModel = MessageModel(idRecipient, message)

        try {
            FirebaseClient.getFirebaseInstance().child("messages")
                .child(idSender)
                .child(idRecipient)
                .push()
                .setValue(messageModel)

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


}