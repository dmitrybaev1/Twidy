package com.example.twidy

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import io.github.luizgrp.sectionedrecyclerviewadapter.Section
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters

class FavoriteSection(private val list: ArrayList<FavoriteItem>,private val title: String) : Section(
    SectionParameters.builder()
        .itemResourceId(R.layout.favorite_item)
        .headerResourceId(R.layout.favorite_header)
        .build()
) {
    override fun getContentItemsTotal(): Int = list.size

    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        holder as FavoriteItemHolder
        Picasso.get().load(list[position].avatar).into(holder.avatarView)
        holder.personNameView.text = list[position].personName
        holder.descriptionView.text = list[position].description
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
}