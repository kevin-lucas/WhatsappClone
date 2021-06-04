package br.com.kevinlucas.whatsappmvvm.service.repository

import android.content.Context
import br.com.kevinlucas.whatsappmvvm.service.constants.WhatsappConstants
import br.com.kevinlucas.whatsappmvvm.service.helper.Base64Custom
import br.com.kevinlucas.whatsappmvvm.service.listener.APIListener
import br.com.kevinlucas.whatsappmvvm.service.model.ContactModel
import br.com.kevinlucas.whatsappmvvm.service.model.UserModel
import br.com.kevinlucas.whatsappmvvm.service.repository.local.SecurityPreferences
import br.com.kevinlucas.whatsappmvvm.service.repository.remote.FirebaseClient
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue

class ContactRepository(context: Context) : BaseRepository(context) {

    private val mSharedPreferences = SecurityPreferences(context)

    fun addContact(email: String, listener: APIListener<Boolean>) {

        val idContact = Base64Custom.encodeBase64(email)

        // Verificando se o exite um contato/usuário com esse email
        val reference = FirebaseClient.getFirebaseInstance().child("users").child(idContact)

        // adiciona um listener ao nó que consulta apenas uma vez
        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.value != null) {
                    listener.onSuccess(true)
                    snapshot.getValue<UserModel>()
                    val contact = snapshot.getValue(UserModel::class.java)
                    if (contact != null) {
                        saveContact(email, contact)
                    }
                } else {
                    listener.onFailure("Contato com o email informado não possui cadastro.")
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun list(listener: APIListener<List<ContactModel>>, event: String) {
        val personKeyUserLogged = mSharedPreferences.get(WhatsappConstants.SHARED.PERSON_KEY)
        val contacts = arrayListOf<ContactModel>()
        val valueListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                // Limpar lista
                contacts.clear()

                for (data: DataSnapshot in snapshot.children) {
                    val contact = data.getValue<ContactModel>()
                    if (contact != null) {
                        contacts.add(contact)
                    }
                }

                if (contacts.isNotEmpty()){
                    listener.onSuccess(contacts)
                } else {
                    listener.onFailure("Nenhum contato encontrado")
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        }

        if (event == "onResume()"){
            FirebaseClient.getFirebaseInstance().child("contacts").child(personKeyUserLogged)
                .addValueEventListener(valueListener)
        } else {
            FirebaseClient.getFirebaseInstance().child("contacts").child(personKeyUserLogged)
                .removeEventListener(valueListener)
        }

    }

    fun all(listener: APIListener<List<ContactModel>>, event: String) {
        list(listener, event)
    }

    private fun saveContact(email: String, contact: UserModel) {
        val personKeyUserLogged = mSharedPreferences.get(WhatsappConstants.SHARED.PERSON_KEY)
        val personKeyContact = Base64Custom.encodeBase64(email)
        val contactModel = ContactModel(contact.id, contact.name, contact.email)

        FirebaseClient.getFirebaseInstance().child("contacts")
            .child(personKeyUserLogged).child(personKeyContact)
            .setValue(contactModel)
    }
}