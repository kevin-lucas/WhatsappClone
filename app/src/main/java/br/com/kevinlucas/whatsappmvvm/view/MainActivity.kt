package br.com.kevinlucas.whatsappmvvm.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import br.com.kevinlucas.whatsappmvvm.R
import br.com.kevinlucas.whatsappmvvm.service.repository.remote.FirebaseClient
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setListeners()
    }

    fun setListeners() {
        button_logout.setOnClickListener(this)
    }

    fun signOut() {

    }

    override fun onClick(v: View) {
        if (v.id == R.id.button_logout) {
            //signOut()
            FirebaseClient.getFirebaseInstanceAuth().signOut()
            val it = Intent(this, LoginActivity::class.java)
            startActivity(it)
            finish()
        }
    }

}