package com.example.twidy.chats.adapters

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.twidy.R
import com.example.twidy.chats.entities.FavoriteItem
import com.github.siyamed.shapeimageview.RoundedImageView
import com.squareup.picasso.Picasso
import io.github.luizgrp.sectionedrecyclerviewadapter.Section
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters

class FavoriteSection(private val list: ArrayList<FavoriteItem>, private val title: String) : Section(
    SectionParameters.builder()
        .itemResourceId(R.layout.favorite_item)
        .headerResourceId(R.layout.favorite_header)
        .build()
) {
    override fun getContentItemsTotal(): Int = list.size

    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        holder as FavoriteItemHolder
        Picasso.get().load(list[position].avatar).into(holder.avatarView)
        holder.apply {
            personNameView.text = list[position].personName
            descriptionView.text = list[position].description
        }
    }

    override fun getItemViewHolder(view: View?): RecyclerView.ViewHolder {
        return FavoriteItemHolder(view!!)
    }

    override fun onBindHeaderViewHolder(holder: RecyclerView.ViewHolder?) {
        holder as FavoriteHeaderHolder
        holder.titleView.text = title
    }

    override fun getHeaderViewHolder(view: View?): RecyclerView.ViewHolder {
        return FavoriteHeaderHolder(view!!)
    }
    inner class FavoriteItemHolder : RecyclerView.ViewHolder{
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
    inner class FavoriteHeaderHolder : RecyclerView.ViewHolder {
        var view: View
        var titleView: TextView
        constructor(v: View) : super(v) {
            view = v
            titleView = v.findViewById(R.id.title_textview)
        }
    }
}