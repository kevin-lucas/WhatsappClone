package br.com.kevinlucas.whatsappmvvm.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.kevinlucas.whatsappmvvm.R
import br.com.kevinlucas.whatsappmvvm.service.constants.WhatsappConstants
import br.com.kevinlucas.whatsappmvvm.service.listener.ContactListener
import br.com.kevinlucas.whatsappmvvm.view.adapter.SimpleDividerItemDecoration
import br.com.kevinlucas.whatsappmvvm.view.adapter.TalkAdapter
import br.com.kevinlucas.whatsappmvvm.viewmodel.ChatViewModel

class TalkFragment : Fragment() {
    private lateinit var mViewModel: ChatViewModel
    private lateinit var mListener: ContactListener
    private val mAdapter = TalkAdapter()
    private var mContactId = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        mViewModel = ViewModelProvider(this).get(ChatViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_talk, container, false)

        val recycler = root.findViewById<RecyclerView>(R.id.recycler_all_talks)

        recycler.addItemDecoration(SimpleDividerItemDecoration(context))
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = mAdapter

        mListener = object : ContactListener {
            override fun onInitTalk(id: String, name: String) {
                val intent = Intent(context, ChatActivity::class.java)
                val bundle = Bundle()
                bundle.putString(WhatsappConstants.BUNDLE.CONTACTID, id)
                bundle.putString(WhatsappConstants.BUNDLE.CONTACTNAME, name)
                intent.putExtras(bundle)
                startActivity(intent)
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
        mViewModel.talks().observe(viewLifecycleOwner, Observer {
            if (it.count() > 0) {
                mAdapter.updateList(it)
            }
        })

    }
}