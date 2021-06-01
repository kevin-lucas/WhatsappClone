package br.com.kevinlucas.whatsappmvvm.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.kevinlucas.whatsappmvvm.service.listener.APIListener
import br.com.kevinlucas.whatsappmvvm.service.listener.ValidationListener
import br.com.kevinlucas.whatsappmvvm.service.model.UserModel
import br.com.kevinlucas.whatsappmvvm.service.repository.PersonRepository
import br.com.kevinlucas.whatsappmvvm.service.repository.remote.FirebaseClient

class RegisterViewModel(application: Application) : AndroidViewModel(application) {

    private val mPersonRepository = PersonRepository(application)

    private val mRegister = MutableLiveData<ValidationListener>()

    fun register(): LiveData<ValidationListener> {
        return mRegister
    }

    fun createUserWithLoginAndPassword(name: String, email: String, password: String) {
        mPersonRepository.create(name, email, password, object : APIListener<Boolean> {
            override fun onSuccess(validation: Boolean) {
                mRegister.value = ValidationListener()
            }

            override fun onFailure(str: String) {
                mRegister.value = ValidationListener(str)
            }

        })
    }

}