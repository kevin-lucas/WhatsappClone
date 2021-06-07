package br.com.kevinlucas.whatsappmvvm.service.repository

import android.content.Context
import br.com.kevinlucas.whatsappmvvm.service.constants.WhatsappConstants
import br.com.kevinlucas.whatsappmvvm.service.listener.APIListener
import br.com.kevinlucas.whatsappmvvm.service.model.ChatModel
import br.com.kevinlucas.whatsappmvvm.service.model.ContactModel
import br.com.kevinlucas.whatsappmvvm.service.model.MessageModel
import br.com.kevinlucas.whatsappmvvm.service.repository.local.SecurityPreferences
import br.com.kevinlucas.whatsappmvvm.service.repository.remote.FirebaseClient
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue

class ChatRepository(context: Context) : BaseRepository(context) {

    private val mSharedPreferences = SecurityPreferences(context)

    fun sendMessage(contactId: String, message: String, listener: APIListener<Boolean>) {

        val idSender = mSharedPreferences.get(WhatsappConstants.SHARED.PERSON_KEY)
        val idRecipient = contactId
        val messageModel = MessageModel(idRecipient, message)

        try {

            // Salva a menssagem para o remetente visualizar
            FirebaseClient.getFirebaseInstance().child("messages")
                .child(idSender)
                .child(idRecipient)
                .push()
                .setValue(messageModel).addOnCompleteListener {
                    if (it.isSuccessful) {
                        listener.onSuccess(true)
                    } else {
                        listener.onFailure("Erro ao enviar a mensagem, tente novamente!")
                    }
                }

            // Salva a menssagem para o destinário visualizar
            FirebaseClient.getFirebaseInstance().child("messages")
                .child(idRecipient)
                .child(idSender)
                .push()
                .setValue(messageModel)

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun loadAllMessages(
        listener: APIListener<List<MessageModel>>,
        event: String,
        idContact: String
    ) {
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

    fun saveChat(idContact: String, nameContact: String, message: String) {

        val idSender = mSharedPreferences.get(WhatsappConstants.SHARED.PERSON_KEY)
        val nameSender = mSharedPreferences.get(WhatsappConstants.SHARED.PERSON_NAME)
        val idRecipient = idContact
        val nameRecipient = nameContact

        val chatSender = ChatModel(idRecipient, nameRecipient, message)
        val chatRecipient = ChatModel(idSender, nameSender, message)

        FirebaseClient.getFirebaseInstance().child("chats")
            .child(idSender)
            .child(idRecipient)
            .setValue(chatSender)

        FirebaseClient.getFirebaseInstance().child("chats")
            .child(idRecipient)
            .child(idSender)
            .setValue(chatRecipient)

    }

    private fun listTalks(listener: APIListener<List<ChatModel>>, event: String) {
        val personKeyUserLogged = mSharedPreferences.get(WhatsappConstants.SHARED.PERSON_KEY)
        val talks = arrayListOf<ChatModel>()
        val valueListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                // Limpar lista
                talks.clear()

                for (data: DataSnapshot in snapshot.children) {
                    val talk = data.getValue<ChatModel>()
                    if (talk != null) {
                        talks.add(talk)
                    }
                }

                if (talks.isNotEmpty()){
                    listener.onSuccess(talks)
                } else {
                    listener.onFailure("Nenhuma conversa encontrada")
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        }

        if (event == "onResume()"){
            FirebaseClient.getFirebaseInstance().child("chats").child(personKeyUserLogged)
                .addValueEventListener(valueListener)
        } else {
            FirebaseClient.getFirebaseInstance().child("chats").child(personKeyUserLogged)
                .removeEventListener(valueListener)
        }

    }

    fun all(listener: APIListener<List<ChatModel>>, event: String) {
        listTalks(listener, event)
    }

}