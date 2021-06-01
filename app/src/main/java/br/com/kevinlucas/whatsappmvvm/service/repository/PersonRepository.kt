package br.com.kevinlucas.whatsappmvvm.service.repository

import android.content.Context
import br.com.kevinlucas.whatsappmvvm.service.model.UserModel
import br.com.kevinlucas.whatsappmvvm.service.repository.local.UserDAO
import br.com.kevinlucas.whatsappmvvm.service.repository.remote.FirebaseClient
import br.com.kevinlucas.whatsappmvvm.service.repository.remote.PersonService
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

class PersonRepository(context: Context) : BaseRepository(context) {
    fun create(name: String, email: String, password: String): Boolean {
        var result = false
        FirebaseClient.getFirebaseInstanceAuth().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    result = it.isSuccessful
                    it.result?.user?.let { it1 -> save(it1.uid, name, email) }
                }
            }
        return result
    }

    private fun save(id: String, name: String, email: String) {
        val ref = FirebaseClient.getFirebaseInstance()
        val user = UserModel(id, name, email)
        ref.child("users").child(id).setValue(user)
    }
}