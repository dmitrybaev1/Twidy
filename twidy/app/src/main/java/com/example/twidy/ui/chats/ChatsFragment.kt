package com.example.twidy.ui.chats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.twidy.R

class ChatsFragment : Fragment() {

    private lateinit var chatsViewModel: ChatsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        chatsViewModel =
                ViewModelProviders.of(this).get(ChatsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_chats, container, false)
        val textView: TextView = root.findViewById(R.id.text_dashboard)
        chatsViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}