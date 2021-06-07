package br.com.kevinlucas.whatsappmvvm.view.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.kevinlucas.whatsappmvvm.R
import br.com.kevinlucas.whatsappmvvm.service.listener.ContactListener
import br.com.kevinlucas.whatsappmvvm.service.model.ChatModel
import br.com.kevinlucas.whatsappmvvm.service.model.ContactModel

class TalkVewHolder(itemView: View, val listener: ContactListener) :
    RecyclerView.ViewHolder(itemView) {

    private var mTextNameContact: TextView = itemView.findViewById(R.id.text_name_contact_talk)
    private var mTextMessageContact: TextView =
        itemView.findViewById(R.id.text_message_talk_contact)

    fun bindData(chat: ChatModel) {
        this.mTextNameContact.text = chat.name
        this.mTextMessageContact.text = chat.message

        mTextNameContact.setOnClickListener {
            listener.onInitTalk(
                chat.idContact.toString(),
                chat.name.toString()
            )
        }
    }
}