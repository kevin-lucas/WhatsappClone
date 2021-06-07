package br.com.kevinlucas.whatsappmvvm.service.repository

import android.content.Context
import br.com.kevinlucas.whatsappmvvm.service.constants.WhatsappConstants
import br.com.kevinlucas.whatsappmvvm.service.helper.Base64Custom
import br.com.kevinlucas.whatsappmvvm.service.listener.APIListener
import br.com.kevinlucas.whatsappmvvm.service.model.UserModel
import br.com.kevinlucas.whatsappmvvm.service.repository.local.SecurityPreferences
import br.com.kevinlucas.whatsappmvvm.service.repository.remote.FirebaseClient
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue

class PersonRepository(context: Context) : BaseRepository(context) {

    private val mSharedPreferences = SecurityPreferences(context)

    fun create(name: String, email: String, password: String, listener: APIListener<Boolean>) {
        FirebaseClient.getFirebaseInstanceAuth().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    listener.onSuccess(it.isSuccessful)
                    save(Base64Custom.encodeBase64(email), name, email)
                } else {
                    val error = try {
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
                    listener.onFailure(error)
                }
            }

    }

    fun login(email: String, password: String, listener: APIListener<Boolean>) {

        val firebaseReference = FirebaseClient.getFirebaseInstance().child("users").child(Base64Custom.encodeBase64(email))

        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val userLogged = snapshot.getValue<UserModel>()
                if (userLogged != null){
                    mSharedPreferences.store(WhatsappConstants.SHARED.PERSON_KEY, userLogged.id.toString())
                    mSharedPreferences.store(WhatsappConstants.SHARED.PERSON_NAME, userLogged.name.toString())
                    mSharedPreferences.store(WhatsappConstants.SHARED.PERSON_EMAIL, userLogged.email.toString())
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        }

        FirebaseClient.getFirebaseInstanceAuth().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    listener.onSuccess(it.isSuccessful)
                    firebaseReference.addListenerForSingleValueEvent(valueEventListener)
                } else {
                    val error = try {
                        throw it.exception!!
                    } catch (e: FirebaseAuthInvalidUserException) {
                        "E-mail inválido ou desabilitado, favor informe um e-mail válido!"
                    } catch (e: FirebaseAuthInvalidCredentialsException) {
                        "Senha incorreta, favor informe uma senha válida!"
                    } catch (e: Exception) {
                        e.printStackTrace()
                        "Erro ao realizar o login, tente novamente!"
                    }
                    listener.onFailure(error)
                }
            }

    }

    fun verifyLoggedUser() = FirebaseClient.getFirebaseInstanceAuth().currentUser != null

    fun signOut(listener: APIListener<Boolean>) {
        val auth = FirebaseClient.getFirebaseInstanceAuth()

        auth.signOut()

        if (auth.currentUser == null) {
            listener.onSuccess(true)
        } else {
            listener.onFailure("Error ao realizar o logout, tente novamente!")
        }
    }

    private fun save(id: String, name: String, email: String) {
        val ref = FirebaseClient.getFirebaseInstance()
        val user = UserModel(id, name, email)
        ref.child("users").child(id).setValue(user)
    }
}