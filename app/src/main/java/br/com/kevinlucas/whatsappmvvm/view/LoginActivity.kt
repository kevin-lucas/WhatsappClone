package br.com.kevinlucas.whatsappmvvm.view

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.kevinlucas.whatsappmvvm.service.constants.WhatsappConstants
import br.com.kevinlucas.whatsappmvvm.R
import br.com.kevinlucas.whatsappmvvm.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mContext: Context
    private lateinit var mViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        this.mContext = this
        this.mViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        // Inicializa eventos
        setListeners();

        // setObservers
        observe()

        verifyLoggedUser()
    }

    override fun onClick(v: View) {
        if (v.id == R.id.button_login) {
            handleLogin()
        } else if (v.id == R.id.text_login_create_account) {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun verifyLoggedUser() {
        mViewModel.verifyLoggedUser()
    }

    private fun setListeners() {
        button_login.setOnClickListener(this)
        text_login_create_account.setOnClickListener(this)
    }

    private fun observe() {
        mViewModel.login().observe(this, Observer {
            if (it.success()) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, it.failure(), Toast.LENGTH_LONG)
                    .show()
            }
        })

        mViewModel.loggedUser().observe(this, Observer {
            if (it) {
                startActivity(Intent(this, MainActivity::class.java))
            }
        })
    }

    private fun handleLogin() {
        val email = edit_login_email.text.toString()
        val password = edit_login_password.text.toString()
        mViewModel.doLogin(email, password)
    }

}