package br.com.kevinlucas.whatsappmvvm.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.kevinlucas.whatsappmvvm.R
import br.com.kevinlucas.whatsappmvvm.service.constants.WhatsappConstants
import br.com.kevinlucas.whatsappmvvm.service.model.MessageModel
import br.com.kevinlucas.whatsappmvvm.service.repository.local.SecurityPreferences
import br.com.kevinlucas.whatsappmvvm.view.viewholder.ChatViewHolder

class ChatAdapter : RecyclerView.Adapter<ChatViewHolder> {

    private var context: Context
    private var mList: List<MessageModel> = arrayListOf()

    constructor(context: Context) {
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        return if (viewType == WhatsappConstants.LAYOUT.MSG_TYPE_LEFT) {
            ChatViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.row_chat_message_recipient, parent, false)
            )
        } else {
            ChatViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.row_chat_message_sender, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.bindData(mList[position])
    }

    override fun getItemCount(): Int {
        return mList.count()
    }

    override fun getItemViewType(position: Int): Int {

        val mSharedPreferences = SecurityPreferences(context)
        val idUserLogged = mSharedPreferences.get(WhatsappConstants.SHARED.PERSON_KEY)
        return if (mList.get(position).idContact.equals(idUserLogged)) {
            WhatsappConstants.LAYOUT.MSG_TYPE_RIGHT
        } else {
            WhatsappConstants.LAYOUT.MSG_TYPE_LEFT
        }

    }

    fun updateList(list: List<MessageModel>) {
        mList = list
        notifyDataSetChanged()
    }
}