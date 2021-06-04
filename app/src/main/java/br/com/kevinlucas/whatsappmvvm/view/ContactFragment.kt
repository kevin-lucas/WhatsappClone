package br.com.kevinlucas.whatsappmvvm.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.kevinlucas.whatsappmvvm.R
import br.com.kevinlucas.whatsappmvvm.service.listener.ContactListener
import br.com.kevinlucas.whatsappmvvm.view.adapter.ContactAdapter
import br.com.kevinlucas.whatsappmvvm.viewmodel.ContactViewModel

class ContactFragment : Fragment() {

    private lateinit var mViewModel: ContactViewModel
    private lateinit var mListener: ContactListener
    private val mAdapter = ContactAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        mViewModel = ViewModelProvider(this).get(ContactViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_contact, container, false)

        val recycler = root.findViewById<RecyclerView>(R.id.recycler_all_contacts)
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = mAdapter

        mListener = object : ContactListener {
            override fun onInitTalk(id: String) {
                // incia conversa
            }
        }

        setObservers()

        return root
    }

    override fun onResume() {
        super.onResume()
        mAdapter.attachListener(mListener)
        mViewModel.list("onResume()")
        Log.i("ValueEventListener", "onResume()")
    }

    override fun onStop() {
        super.onStop()
        mViewModel.list("onStart()")
        Log.i("ValueEventListener", "onStop()")
    }

    private fun setObservers() {
        mViewModel.contacts().observe(viewLifecycleOwner, Observer {
            if (it.count() > 0) {
                mAdapter.updateList(it)
            }
        })

        mViewModel.validation().observe(viewLifecycleOwner, Observer {
            if (it.success()) {
                Toast.makeText(context, "Sucesso na ação", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, it.failure(), Toast.LENGTH_SHORT).show()
            }
        })
    }
}