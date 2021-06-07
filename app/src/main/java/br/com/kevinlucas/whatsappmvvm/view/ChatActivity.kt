package br.com.kevinlucas.whatsappmvvm.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.kevinlucas.whatsappmvvm.R
import br.com.kevinlucas.whatsappmvvm.service.constants.WhatsappConstants
import br.com.kevinlucas.whatsappmvvm.view.adapter.ChatAdapter
import br.com.kevinlucas.whatsappmvvm.viewmodel.ChatViewModel
import kotlinx.android.synthetic.main.activity_chat.*

class ChatActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mViewModel: ChatViewModel
    private lateinit var mContext: Context
    private lateinit var toolbar: Toolbar
    private val mAdapter = ChatAdapter(this)
    private var mContactId = ""
    private var mContactName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        mViewModel = ViewModelProvider(this).get(ChatViewModel::class.java)
        mContext = this

        // Toolbar
        toolbar = findViewById(R.id.tb_chat)
        toolbar.title = "Usuário"
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow_left)
        setSupportActionBar(toolbar)

        // RecyclerView
        val recycler = findViewById<RecyclerView>(R.id.recycler_chat_messages)
        recycler.layoutManager = LinearLayoutManager(mContext)
        recycler.adapter = mAdapter

        loadDataFormActivity()
        setListeners()
        setObservers()
    }

    private fun loadDataFormActivity() {
        val bundle = intent.extras
        if (bundle != null) {
            mContactId = bundle.getString(WhatsappConstants.BUNDLE.CONTACTID).toString()
            mContactName = bundle.getString(WhatsappConstants.BUNDLE.CONTACTNAME).toString()
            toolbar.title = mContactName
        }
    }

    override fun onResume() {
        super.onResume()
        mViewModel.loadMessages(mContactId, "onResume()")
    }

    override fun onStop() {
        super.onStop()
        mViewModel.loadMessages(mContactId, "onStop()")
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

    private fun setObservers() {
        mViewModel.messages().observe(this, Observer {
            if (it.count() > 0) {
                mAdapter.updateList(it)
            }
        })
    }
}