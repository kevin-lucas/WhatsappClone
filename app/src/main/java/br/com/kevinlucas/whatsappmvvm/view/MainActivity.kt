package br.com.kevinlucas.whatsappmvvm.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.Toolbar
import br.com.kevinlucas.whatsappmvvm.R
import br.com.kevinlucas.whatsappmvvm.service.repository.remote.FirebaseClient
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar_main)
        toolbar.setTitle("WhatsApp")

        setSupportActionBar(toolbar)

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return true
    }

}