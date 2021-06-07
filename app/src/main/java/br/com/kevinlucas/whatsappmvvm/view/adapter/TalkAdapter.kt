package br.com.kevinlucas.whatsappmvvm.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.kevinlucas.whatsappmvvm.R
import br.com.kevinlucas.whatsappmvvm.service.listener.ContactListener
import br.com.kevinlucas.whatsappmvvm.service.model.ChatModel
import br.com.kevinlucas.whatsappmvvm.view.viewholder.TalkVewHolder

class TalkAdapter : RecyclerView.Adapter<TalkVewHolder>() {

    private var mList: List<ChatModel> = arrayListOf()
    private lateinit var mListener: ContactListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TalkVewHolder {
        val item =
            LayoutInflater.from(parent.context).inflate(R.layout.row_talk_list, parent, false)
        return TalkVewHolder(item, mListener)
    }

    override fun onBindViewHolder(holder: TalkVewHolder, position: Int) {
        holder.bindData(mList[position])
    }

    override fun getItemCount(): Int {
        return mList.count()
    }

    fun attachListener(listener: ContactListener) {
        mListener = listener
    }

    fun updateList(list: List<ChatModel>) {
        mList = list
        notifyDataSetChanged()
    }

}