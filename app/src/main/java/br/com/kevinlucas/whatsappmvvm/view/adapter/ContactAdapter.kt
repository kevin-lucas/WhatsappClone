package br.com.kevinlucas.whatsappmvvm.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.kevinlucas.whatsappmvvm.R
import br.com.kevinlucas.whatsappmvvm.service.listener.ContactListener
import br.com.kevinlucas.whatsappmvvm.service.model.ContactModel
import br.com.kevinlucas.whatsappmvvm.view.viewholder.ContactViewHolder

class ContactAdapter : RecyclerView.Adapter<ContactViewHolder>() {

    private var mList: List<ContactModel> = arrayListOf()
    private lateinit var mListener: ContactListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val item =
            LayoutInflater.from(parent.context).inflate(R.layout.row_contact_list, parent, false)
        return ContactViewHolder(item, mListener)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bindData(mList[position])
    }

    override fun getItemCount(): Int {
        return mList.count()
    }

    fun attachListener(listener: ContactListener) {
        mListener = listener
    }

    fun updateList(list: List<ContactModel>) {
        mList = list
        notifyDataSetChanged()
    }
}