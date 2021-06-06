package br.com.kevinlucas.whatsappmvvm.view.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.kevinlucas.whatsappmvvm.R
import br.com.kevinlucas.whatsappmvvm.service.listener.ContactListener
import br.com.kevinlucas.whatsappmvvm.service.model.ContactModel

class ContactViewHolder(itemView: View, val listener: ContactListener) :
    RecyclerView.ViewHolder(itemView) {

    private var mTextNameContact: TextView = itemView.findViewById(R.id.text_name_contact)
    private var mTextEmailContact: TextView = itemView.findViewById(R.id.text_email_contact)

    fun bindData(contact: ContactModel) {

        this.mTextNameContact.text = contact.name
        this.mTextEmailContact.text = contact.email

        mTextNameContact.setOnClickListener {
            listener.onInitTalk(
                contact.id.toString(),
                contact.name.toString()
            )
        }
    }


}