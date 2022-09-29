package com.example.twidy.recommendations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels

class RecommendationsFragment : Fragment() {

    private val recommendationsViewModel: RecommendationsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_recommendations, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        recommendationsViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }
}