package br.com.kevinlucas.whatsappmvvm.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.kevinlucas.whatsappmvvm.R
import br.com.kevinlucas.whatsappmvvm.service.repository.remote.FirebaseClient
import br.com.kevinlucas.whatsappmvvm.viewmodel.RegisterViewModel
import com.google.android.gms.tasks.OnCompleteListener
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mViewModel: RegisterViewModel
    private lateinit var mContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        mViewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
        mContext = this

        observe()
        setListeners()
    }

    override fun onClick(v: View) {
        if (v.id == R.id.button_register) {
            handleRegister()
        }
    }

    private fun setListeners() {
        button_register.setOnClickListener(this)
    }

    private fun observe() {
        mViewModel.register().observe(this, Observer {
            if (it.success()) {
                Toast.makeText(this, "Usuário criado com sucesso!", Toast.LENGTH_SHORT).show()
                val it = Intent(this, MainActivity::class.java)
                startActivity(it)
                finish()
            } else {
                Toast.makeText(this, it.failure(), Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun handleRegister() {
        val name = edit_register_name.text.toString()
        val email = edit_register_email.text.toString()
        val password = edit_register_password.text.toString()

        mViewModel.createUserWithLoginAndPassword(name, email, password)
    }
}