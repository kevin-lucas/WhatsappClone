package br.com.kevinlucas.whatsappmvvm.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.kevinlucas.whatsappmvvm.service.constants.WhatsappConstants
import br.com.kevinlucas.whatsappmvvm.service.listener.APIListener
import br.com.kevinlucas.whatsappmvvm.service.listener.ValidationListener
import br.com.kevinlucas.whatsappmvvm.service.repository.ContactRepository
import br.com.kevinlucas.whatsappmvvm.service.repository.PersonRepository
import br.com.kevinlucas.whatsappmvvm.service.repository.local.SecurityPreferences

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val mPersonRepository = PersonRepository(application)
    private val mContactRepository = ContactRepository(application)
    private val mSignOut = MutableLiveData<ValidationListener>()
    private val mAddContact = MutableLiveData<ValidationListener>()
    private val mSharedPreferences = SecurityPreferences(application)

    fun signOut(): LiveData<ValidationListener> {
        return mSignOut
    }

    fun addContact(): LiveData<ValidationListener> {
        return mAddContact
    }

    fun logout() {
        mPersonRepository.signOut(object : APIListener<Boolean> {
            override fun onSuccess(validation: Boolean) {

                mSharedPreferences.remove(WhatsappConstants.SHARED.PERSON_KEY)
                mSharedPreferences.remove(WhatsappConstants.SHARED.PERSON_EMAIL)

                mSignOut.value = ValidationListener()
            }

            override fun onFailure(str: String) {
                mSignOut.value = ValidationListener(str)
            }
        })
    }

    fun addContact(email: String) {
        mContactRepository.addContact(email, object : APIListener<Boolean> {
            override fun onSuccess(validation: Boolean) {
                mAddContact.value = ValidationListener()
            }

            override fun onFailure(str: String) {
                mAddContact.value = ValidationListener(str)
            }
        })
    }

}