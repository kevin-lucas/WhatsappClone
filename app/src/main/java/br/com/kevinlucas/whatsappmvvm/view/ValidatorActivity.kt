package br.com.kevinlucas.whatsappmvvm.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.kevinlucas.whatsappmvvm.R
import br.com.kevinlucas.whatsappmvvm.viewmodel.ValidatorViewModel
import com.github.rtoshiro.util.format.SimpleMaskFormatter
import com.github.rtoshiro.util.format.text.MaskTextWatcher
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_validador.*

class ValidatorActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mValidatorViewModel: ValidatorViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_validador)

        this.mValidatorViewModel = ViewModelProvider(this).get(ValidatorViewModel::class.java)

        setMaskFormatter()
        setListeners()
        observe()
    }

    override fun onClick(v: View) {
        if (v.id == R.id.btn_validator){
            val token = edit_code_validator.text.toString()
            mValidatorViewModel.validateToken(token)
        }
    }

    private fun setListeners(){
        btn_validator.setOnClickListener(this)
    }

    private fun observe(){
        mValidatorViewModel.token().observe(this, Observer {
            if (it) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Código inválido, tente novamente!", Toast.LENGTH_LONG)
                    .show()
            }
        })
    }

    private fun setMaskFormatter(){
        var smf = SimpleMaskFormatter("NNNN")
        val mtwCode = MaskTextWatcher(edit_code_validator, smf)
        edit_code_validator.addTextChangedListener(mtwCode)
    }
}