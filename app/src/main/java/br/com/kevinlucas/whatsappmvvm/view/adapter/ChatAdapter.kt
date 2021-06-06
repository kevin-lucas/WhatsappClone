package br.com.kevinlucas.whatsappmvvm.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.kevinlucas.whatsappmvvm.R
import br.com.kevinlucas.whatsappmvvm.service.model.MessageModel
import br.com.kevinlucas.whatsappmvvm.view.viewholder.ChatViewHolder

class ChatAdapter : RecyclerView.Adapter<ChatViewHolder>() {

    private var mList: List<MessageModel> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val item =
            LayoutInflater.from(parent.context).inflate(R.layout.row_chat_message, parent, false)
        return ChatViewHolder(item)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.bindData(mList[position])
    }

    override fun getItemCount(): Int {
        return mList.count()
    }

    fun updateList(list: List<MessageModel>) {
        mList = list
        notifyDataSetChanged()
    }
}