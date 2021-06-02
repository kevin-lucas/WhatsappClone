package br.com.kevinlucas.whatsappmvvm.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.kevinlucas.whatsappmvvm.R
import br.com.kevinlucas.whatsappmvvm.service.repository.remote.FirebaseClient
import br.com.kevinlucas.whatsappmvvm.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var toolbar: Toolbar
    private lateinit var mViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar_main)
        toolbar.title = "WhatsApp"

        mViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        setSupportActionBar(toolbar)

        setListeners()

        observer()
    }

    fun setListeners() {
        button_logout.setOnClickListener(this)
    }

    fun signOut() {
        mViewModel.logout()
    }

    fun observer() {
        mViewModel.signOut().observe(this, Observer {
            if (it.success()) {
                val it = Intent(this, LoginActivity::class.java)
                startActivity(it)
                finish()
            } else {
                Toast.makeText(this, it.failure(), Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onClick(v: View) {
        if (v.id == R.id.button_logout) {
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.action_exit -> {
                signOut()
                true
            }
            R.id.action_settings -> {
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }

    }

}