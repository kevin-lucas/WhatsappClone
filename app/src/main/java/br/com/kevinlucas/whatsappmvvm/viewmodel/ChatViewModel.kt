package br.com.kevinlucas.whatsappmvvm.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.kevinlucas.whatsappmvvm.service.listener.ValidationListener
import br.com.kevinlucas.whatsappmvvm.service.repository.ChatRepository

class ChatViewModel(application: Application) : AndroidViewModel(application) {
    private val mChatRepository = ChatRepository(application)

    private val mSendMessage = MutableLiveData<Boolean>()
    private val mValidation = MutableLiveData<ValidationListener>()

    fun message() : LiveData<Boolean>{
        return mSendMessage
    }

    fun sendMessageChat(contactId: String, message: String){
        mChatRepository.sendMessage(contactId, message)
    }
}