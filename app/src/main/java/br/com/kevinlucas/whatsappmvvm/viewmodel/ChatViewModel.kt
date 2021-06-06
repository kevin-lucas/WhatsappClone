package br.com.kevinlucas.whatsappmvvm.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.kevinlucas.whatsappmvvm.service.listener.APIListener
import br.com.kevinlucas.whatsappmvvm.service.listener.ValidationListener
import br.com.kevinlucas.whatsappmvvm.service.model.ContactModel
import br.com.kevinlucas.whatsappmvvm.service.model.MessageModel
import br.com.kevinlucas.whatsappmvvm.service.repository.ChatRepository

class ChatViewModel(application: Application) : AndroidViewModel(application) {
    private val mChatRepository = ChatRepository(application)

    private val mSendMessage = MutableLiveData<Boolean>()
    private val mMessagesList = MutableLiveData<List<MessageModel>>()
    private val mValidation = MutableLiveData<ValidationListener>()

    fun send(): LiveData<Boolean> {
        return mSendMessage
    }

    fun messages(): LiveData<List<MessageModel>> {
        return mMessagesList
    }

    fun sendMessageChat(contactId: String, message: String) {
        mChatRepository.sendMessage(contactId, message)
    }

    fun loadMessages(contactId: String, event: String) {
        val listener = object : APIListener<List<MessageModel>> {
            override fun onSuccess(model: List<MessageModel>) {
                mMessagesList.value = model
            }

            override fun onFailure(str: String) {
                mMessagesList.value = arrayListOf()
                mValidation.value = ValidationListener(str)
            }
        }

        mChatRepository.loadAllMessages(listener, event, contactId)
    }
}