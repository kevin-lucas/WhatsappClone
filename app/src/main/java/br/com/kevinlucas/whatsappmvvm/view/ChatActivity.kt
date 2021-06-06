package br.com.kevinlucas.whatsappmvvm.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import br.com.kevinlucas.whatsappmvvm.R

class ChatActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        toolbar = findViewById(R.id.tb_chat)

        toolbar.title = "Usuário"
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow_left)
        setSupportActionBar(toolbar)
    }
}