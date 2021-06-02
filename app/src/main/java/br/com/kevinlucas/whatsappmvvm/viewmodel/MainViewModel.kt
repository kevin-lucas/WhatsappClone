package br.com.kevinlucas.whatsappmvvm.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.kevinlucas.whatsappmvvm.service.listener.APIListener
import br.com.kevinlucas.whatsappmvvm.service.listener.ValidationListener
import br.com.kevinlucas.whatsappmvvm.service.repository.PersonRepository

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val mPersonRepository = PersonRepository(application)
    private val mSignOut = MutableLiveData<ValidationListener>()

    fun signOut(): LiveData<ValidationListener> {
        return mSignOut
    }

    fun logout() {
        mPersonRepository.signOut(object : APIListener<Boolean> {
            override fun onSuccess(validation: Boolean) {
                mSignOut.value = ValidationListener()
            }

            override fun onFailure(str: String) {
                mSignOut.value = ValidationListener(str)
            }
        })
    }

}