package com.example.twidy.ui.chats.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.twidy.ui.chats.items.ChatItem
import com.example.twidy.R
import com.example.twidy.ui.MainActivity
import com.example.twidy.ui.chats.ChatsViewModel
import com.github.siyamed.shapeimageview.RoundedImageView
import com.squareup.picasso.Picasso

class ChatsAdapter(private val list: List<ChatItem>,private val activity: MainActivity,private val vm: ChatsViewModel) : RecyclerView.Adapter<ChatsAdapter.ChatsHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatsHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.chat_item,parent,false)
        return ChatsHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ChatsHolder, position: Int) {
        if(list[position].type!="user")
            holder.newMessagesView.background = activity.resources.getDrawable(R.drawable.channel_new_textview)
        if(list[position].newMessages==0)
            holder.newMessagesView.visibility = View.GONE
        Picasso.get().load(list[position].avatar).into(holder.avatarView)
        holder.apply {
            personNameView.text = list[position].name
            lastMessageView.text = list[position].lastMessage
            newMessagesView.text = list[position].newMessages.toString()
        }
        if(list[position].inCheckedMode)
            holder.checkBox.visibility = View.VISIBLE
        else
            holder.checkBox.visibility = View.GONE
        holder.checkBox.isChecked = list[position].checked
        var listener = View.OnClickListener{
            if(vm.isToolbarInEditMode) {
                list[position].checked = !list[position].checked
                notifyItemChanged(position)
            }
        }
        holder.apply {
            view.setOnClickListener(listener)
            checkBox.setOnClickListener(listener)
        }
    }

    fun setItemsCheckedMode(){
        for (i in list.indices) {
            list[i].inCheckedMode = true
            notifyItemChanged(i)
        }
    }

    fun unsetItemsCheckedMode(){
        for (i in list.indices) {
            list[i].inCheckedMode = false
            list[i].checked = false
            notifyItemChanged(i)
        }
    }

    inner class ChatsHolder : RecyclerView.ViewHolder{
        var view: View
        var checkBox: CheckBox
        var avatarView: RoundedImageView
        var personNameView: TextView
        var lastMessageView: TextView
        var newMessagesView: TextView
        constructor(v: View) : super(v) {
            view = v
            checkBox = v.findViewById(R.id.checkBox)
            avatarView = v.findViewById(R.id.avatar_view)
            personNameView = v.findViewById(R.id.person_name_view)
            lastMessageView = v.findViewById(R.id.last_message_view)
            newMessagesView = v.findViewById(R.id.cost_view)
        }
    }

    /*fun changeMode(){
        if (!activity.isInEditModeChats) {
            activity.isInEditModeChats = true
            activity.navView.visibility = View.GONE
            activity.setSupportActionBar(activity.toolbar)
            activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
            for (i in list.indices) {
                list[i].inCheckedMode = true
                notifyItemChanged(i)
            }
        } else {
            activity.isInEditModeChats = false
            activity.navView.visibility = View.VISIBLE
            activity.setSupportActionBar(activity.toolbar)
            activity.supportActionBar?.setDisplayHomeAsUpEnabled(false)
            for (i in list.indices) {
                list[i].inCheckedMode = false
                list[i].checked = false
                notifyItemChanged(i)
            }
        }*/
}