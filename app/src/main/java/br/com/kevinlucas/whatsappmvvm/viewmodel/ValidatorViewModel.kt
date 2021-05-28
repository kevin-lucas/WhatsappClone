package br.com.kevinlucas.whatsappmvvm.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.kevinlucas.whatsappmvvm.service.constants.WhatsappConstants
import br.com.kevinlucas.whatsappmvvm.service.repository.local.SecurityPreferences

class ValidatorViewModel(application: Application) : AndroidViewModel(application) {

    private val mSharedPreferences = SecurityPreferences(application)

    private val mToken = MutableLiveData<Boolean>()

    fun token(): LiveData<Boolean> {
        return mToken
    }

    fun validateToken(tokenUser: String) {
        val token = mSharedPreferences.get(WhatsappConstants.SHARED.TOKEN_KEY_TEMP)
        mToken.value = if (token == tokenUser) {
            mSharedPreferences.store(WhatsappConstants.SHARED.TOKEN_KEY, token)
            true
        } else {
            false
        }
    }

}