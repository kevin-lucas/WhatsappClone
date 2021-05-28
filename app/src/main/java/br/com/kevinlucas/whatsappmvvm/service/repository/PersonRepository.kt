package br.com.kevinlucas.whatsappmvvm.service.repository

import android.content.Context
import br.com.kevinlucas.whatsappmvvm.service.repository.remote.FirebaseClient
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

class PersonRepository(context: Context) : BaseRepository(context) {

    fun register(name: String, email: String, password: String): Boolean {
        var result = false
        FirebaseClient.getFirebaseInstanceAuth().createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            result = it.isSuccessful
        }
        return result
    }
}