package com.example.twidy.calls

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels


class CallsFragment : Fragment() {

    private val callsViewModel: CallsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_calls, container, false)
        val textView: TextView = root.findViewById(R.id.text_notifications)

        callsViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }
}