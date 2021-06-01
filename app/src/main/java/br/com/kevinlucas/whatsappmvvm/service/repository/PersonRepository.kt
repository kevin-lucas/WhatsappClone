package br.com.kevinlucas.whatsappmvvm.service.repository

import android.content.Context
import br.com.kevinlucas.whatsappmvvm.service.listener.APIListener
import br.com.kevinlucas.whatsappmvvm.service.model.UserModel
import br.com.kevinlucas.whatsappmvvm.service.repository.remote.FirebaseClient
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

class PersonRepository(context: Context) : BaseRepository(context) {
    fun create(name: String, email: String, password: String, listener: APIListener<Boolean>) {

        FirebaseClient.getFirebaseInstanceAuth().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    listener.onSuccess(it.isSuccessful)
                    it.result?.user?.let { it1 -> save(it1.uid, name, email) }
                } else {
                    val msg = try {
                        throw it.exception!!
                    } catch (e: FirebaseAuthWeakPasswordException) {
                        "Digite uma senha mais forte, contendo mais caracteres com letras e números!"
                    } catch (e: FirebaseAuthInvalidCredentialsException) {
                        "O e-mail digitado é inválido, digite um novo e-mail!"
                    } catch (e: FirebaseAuthUserCollisionException) {
                        "Este e-mail já está em uso no aplicativo, informe um diferente!"
                    } catch (e: Exception) {
                        e.printStackTrace()
                        "Algum erro inesperado ocorreu, tente novamente!"
                    }
                    listener.onFailure(msg)
                }
            }

    }

    private fun save(id: String, name: String, email: String) {
        val ref = FirebaseClient.getFirebaseInstance()
        val user = UserModel(name, email)
        ref.child("users").child(id).setValue(user)
    }
}