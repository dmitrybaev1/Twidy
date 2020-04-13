package com.example.twidy

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.siyamed.shapeimageview.RoundedImageView

class FavoriteItemHolder : RecyclerView.ViewHolder{
    var view: View
    var avatarView: RoundedImageView
    var personNameView: TextView
    var descriptionView: TextView
    constructor(v: View) : super(v) {
        view = v
        avatarView = v.findViewById(R.id.avatar_view)
        personNameView = v.findViewById(R.id.person_name_view)
        descriptionView = v.findViewById(R.id.description_view)
    }
}