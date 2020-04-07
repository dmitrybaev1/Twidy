package com.example.twidy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.siyamed.shapeimageview.RoundedImageView

class FavoriteAdapter(var list: ArrayList<FavoriteItem>) : RecyclerView.Adapter<FavoriteAdapter.PopupHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopupHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.popup_item,parent,false)
        return PopupHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: PopupHolder, position: Int) {
        holder.personNameView.text = list[position].personName
        holder.descriptionView.text = list[position].description
    }
    inner class PopupHolder : RecyclerView.ViewHolder{
        var view: View
        var avatarView: RoundedImageView
        var personNameView: TextView
        var descriptionView:TextView
        constructor(v: View) : super(v) {
            view = v
            avatarView = v.findViewById(R.id.avatar_view)
            personNameView = v.findViewById(R.id.person_name_view)
            descriptionView = v.findViewById(R.id.description_view)
        }
    }
}