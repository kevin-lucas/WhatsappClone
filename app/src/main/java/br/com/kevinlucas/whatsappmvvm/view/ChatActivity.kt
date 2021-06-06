package br.com.kevinlucas.whatsappmvvm.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import br.com.kevinlucas.whatsappmvvm.R
import br.com.kevinlucas.whatsappmvvm.service.constants.WhatsappConstants
import br.com.kevinlucas.whatsappmvvm.viewmodel.ChatViewModel
import br.com.kevinlucas.whatsappmvvm.viewmodel.ContactViewModel
import kotlinx.android.synthetic.main.activity_chat.*

class ChatActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mViewModel: ChatViewModel
    private lateinit var toolbar: Toolbar
    private var mContactId = ""
    private var mContactName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        mViewModel = ViewModelProvider(this).get(ChatViewModel::class.java)

        toolbar = findViewById(R.id.tb_chat)

        toolbar.title = "Usuário"
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow_left)
        setSupportActionBar(toolbar)

        setListeners()
        loadDataFormActivity()
    }

    private fun loadDataFormActivity() {
        val bundle = intent.extras
        if (bundle != null) {
            mContactId = bundle.getString(WhatsappConstants.BUNDLE.CONTACTID).toString()
            mContactName = bundle.getString(WhatsappConstants.BUNDLE.CONTACTNAME).toString()
            toolbar.title = mContactName
        }
    }

    private fun setListeners() {
        button_chat_send_message.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        if (v.id == R.id.button_chat_send_message) {
            handleInitChat(mContactId)
        }
    }

    private fun handleInitChat(contactId: String) {
        val message = edit_chat_message.text.toString()
        if (!message.isEmpty()) {
            mViewModel.sendMessageChat(contactId, message)
            edit_chat_message.setText("")
        } else {
            Toast.makeText(this, "Informe uma menssagem para ser enviada", Toast.LENGTH_SHORT)
                .show()
        }

    }
}