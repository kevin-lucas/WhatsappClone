package br.com.kevinlucas.whatsappmvvm.service.repository

import android.content.Context
import br.com.kevinlucas.whatsappmvvm.service.constants.WhatsappConstants
import br.com.kevinlucas.whatsappmvvm.service.listener.APIListener
import br.com.kevinlucas.whatsappmvvm.service.model.MessageModel
import br.com.kevinlucas.whatsappmvvm.service.repository.local.SecurityPreferences
import br.com.kevinlucas.whatsappmvvm.service.repository.remote.FirebaseClient
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue

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

    fun loadAllMessages(listener: APIListener<List<MessageModel>>, event: String, idContact: String) {
        val idSender = mSharedPreferences.get(WhatsappConstants.SHARED.PERSON_KEY)
        val idRecipient = idContact
        val messages = arrayListOf<MessageModel>()

        val valueListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                // Limpar lista
                messages.clear()

                for (data: DataSnapshot in snapshot.children) {
                    val message = data.getValue<MessageModel>()
                    if (message != null) {
                        messages.add(message)
                    }
                }

                if (messages.isNotEmpty()) {
                    listener.onSuccess(messages)
                } else {
                    listener.onFailure("Nenhum contato encontrado")
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        }

        if (event == "onResume()") {
            FirebaseClient.getFirebaseInstance().child("messages")
                .child(idSender)
                .child(idRecipient)
                .addValueEventListener(valueListener)
        } else {
            FirebaseClient.getFirebaseInstance().child("messages")
                .child(idSender)
                .child(idRecipient)
                .removeEventListener(valueListener)
        }

    }

}