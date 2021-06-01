package br.com.kevinlucas.whatsappmvvm.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.kevinlucas.whatsappmvvm.service.model.UserModel
import br.com.kevinlucas.whatsappmvvm.service.repository.PersonRepository
import br.com.kevinlucas.whatsappmvvm.service.repository.remote.FirebaseClient

class RegisterViewModel(application: Application) : AndroidViewModel(application) {

    private val mPersonRepository = PersonRepository(application)

    private val mRegister = MutableLiveData<Boolean>()

    fun register(): LiveData<Boolean> {
        return mRegister
    }

    fun createUserWithLoginAndPassword(name: String, email: String, password: String) {
        mRegister.value = mPersonRepository.create(name, email, password)
    }

}