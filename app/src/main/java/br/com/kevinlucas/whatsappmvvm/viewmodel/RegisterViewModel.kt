package br.com.kevinlucas.whatsappmvvm.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import br.com.kevinlucas.whatsappmvvm.service.repository.remote.FirebaseClient

class RegisterViewModel(application: Application) : AndroidViewModel(application) {

    fun createUserWithLoginAndPassword(email: String, password: String){
        FirebaseClient.getFirebaseInstance().child("points").setValue("50")
    }

}