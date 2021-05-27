package br.com.kevinlucas.whatsappmvvm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlin.random.Random

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val mPersonRepository = PersonRepository(application)

    private val mLogin = MutableLiveData<Boolean>()

    fun login(): LiveData<Boolean> {
        return mLogin
    }

    fun doLogin(phone: String) {
        val random = Random.nextInt(9999 - 1000) + 1000
        val token = random.toString()

        val message = "WhatsApp Código de Confirmação: $token"
        mLogin.value = mPersonRepository.login(phone, message)
    }

}