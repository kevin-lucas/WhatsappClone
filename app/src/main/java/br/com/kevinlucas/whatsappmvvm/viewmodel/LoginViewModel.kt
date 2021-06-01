package br.com.kevinlucas.whatsappmvvm.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.kevinlucas.whatsappmvvm.service.constants.WhatsappConstants
import br.com.kevinlucas.whatsappmvvm.service.listener.APIListener
import br.com.kevinlucas.whatsappmvvm.service.listener.ValidationListener
import br.com.kevinlucas.whatsappmvvm.service.repository.PersonRepository
import br.com.kevinlucas.whatsappmvvm.service.repository.local.SecurityPreferences
import kotlin.random.Random

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val mPersonRepository = PersonRepository(application)
    private val mLogin = MutableLiveData<ValidationListener>()
    private val mLoggedUser = MutableLiveData<Boolean>()

    fun login(): LiveData<ValidationListener> {
        return mLogin
    }

    fun loggedUser(): LiveData<Boolean> {
        return mLoggedUser
    }

    fun doLogin(email: String, password: String) {
        mPersonRepository.login(email, password, object : APIListener<Boolean> {
            override fun onSuccess(validation: Boolean) {
                mLogin.value = ValidationListener()
            }

            override fun onFailure(str: String) {
                mLogin.value = ValidationListener(str)
            }

        })
    }

    /**
    * Verifica se usuário está logado
    */
    fun verifyLoggedUser() {
        val logged = mPersonRepository.verifyLoggedUser()
//        if (!logged) {
//            mPriorityRepository.all()
//        }
        mLoggedUser.value = logged
    }

}