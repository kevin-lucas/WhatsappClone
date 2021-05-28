package br.com.kevinlucas.whatsappmvvm.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.kevinlucas.whatsappmvvm.service.constants.WhatsappConstants
import br.com.kevinlucas.whatsappmvvm.service.repository.PersonRepository
import br.com.kevinlucas.whatsappmvvm.service.repository.local.SecurityPreferences
import kotlin.random.Random

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val mPersonRepository = PersonRepository(application)
    private val mSharedPreferences = SecurityPreferences(application)

    private val mLogin = MutableLiveData<Boolean>()
    private val mLoggedUser = MutableLiveData<Boolean>()

    fun login(): LiveData<Boolean> {
        return mLogin
    }

    fun loggedUser(): LiveData<Boolean> {
        return mLoggedUser
    }

    fun doLogin(phone: String) {
        val random = Random.nextInt(9999 - 1000) + 1000
        val token = random.toString()
        val message = "WhatsApp Código de Confirmação: $token"

        mLogin.value = if (mPersonRepository.login(phone, message)) {
            mSharedPreferences.store(WhatsappConstants.SHARED.TOKEN_KEY_TEMP, token)
            true
        } else {
            false
        }

    }

    /**
    * Verifica se usuário está logado
    */
    fun verifyLoggedUser() {

        val token = mSharedPreferences.get(WhatsappConstants.SHARED.TOKEN_KEY)

        // RetrofitClient.addHeader(token, person)

        val logged = (token != "")
//
//        if (!logged) {
//            mPriorityRepository.all()
//        }

        mLoggedUser.value = logged

    }

}