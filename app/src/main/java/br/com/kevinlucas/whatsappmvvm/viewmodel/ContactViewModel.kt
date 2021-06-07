package br.com.kevinlucas.whatsappmvvm.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.kevinlucas.whatsappmvvm.service.listener.APIListener
import br.com.kevinlucas.whatsappmvvm.service.listener.ValidationListener
import br.com.kevinlucas.whatsappmvvm.service.model.ChatModel
import br.com.kevinlucas.whatsappmvvm.service.model.ContactModel
import br.com.kevinlucas.whatsappmvvm.service.repository.ContactRepository

class ContactViewModel(application: Application) : AndroidViewModel(application) {

    private val mContactRepository = ContactRepository(application)

    private val mContactList = MutableLiveData<List<ContactModel>>()
    private val mValidation = MutableLiveData<ValidationListener>()

    fun contacts(): LiveData<List<ContactModel>> {
        return mContactList
    }

    fun validation(): LiveData<ValidationListener> {
        return mValidation
    }

    fun list(event: String) {
        val listener = object : APIListener<List<ContactModel>> {
            override fun onSuccess(model: List<ContactModel>) {
                mContactList.value = model
            }

            override fun onFailure(str: String) {
                mContactList.value = arrayListOf()
                mValidation.value = ValidationListener(str)
            }
        }

        mContactRepository.all(listener, event)

    }
}