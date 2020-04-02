package com.example.twidy.ui.recommendations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.twidy.R

class RecommendationsFragment : Fragment() {

    private lateinit var recommendationsViewModel: RecommendationsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        recommendationsViewModel =
                ViewModelProviders.of(this).get(RecommendationsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_recommendations, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        recommendationsViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}