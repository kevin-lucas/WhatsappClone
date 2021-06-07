package br.com.kevinlucas.whatsappmvvm.view.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.kevinlucas.whatsappmvvm.R
import br.com.kevinlucas.whatsappmvvm.service.model.MessageModel

class ChatViewHolder(itemView: View) :
    RecyclerView.ViewHolder(itemView) {

    private var mTextChatMessage: TextView =
        itemView.findViewById(R.id.text_chat_message)

    fun bindData(message: MessageModel) {
        this.mTextChatMessage.text = message.message
    }
}