package com.example.twidy

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.siyamed.shapeimageview.RoundedImageView

class FavoriteHeaderHolder : RecyclerView.ViewHolder {
    var view: View
    var titleView: TextView
    constructor(v: View) : super(v) {
        view = v
        titleView = v.findViewById(R.id.title_textview)
    }
}