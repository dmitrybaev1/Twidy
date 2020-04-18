package com.example.twidy.ui.chats.items

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.twidy.R

class FavoriteHeaderHolder : RecyclerView.ViewHolder {
    var view: View
    var titleView: TextView
    constructor(v: View) : super(v) {
        view = v
        titleView = v.findViewById(R.id.title_textview)
    }
}