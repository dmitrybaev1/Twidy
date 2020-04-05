package com.example.twidy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.siyamed.shapeimageview.RoundedImageView

class ChatsAdapter(var list: ArrayList<ChatItem>) : RecyclerView.Adapter<ChatsAdapter.ChatsHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatsHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.chat_item,parent,false)
        return ChatsHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ChatsHolder, position: Int) {
        holder.personNameView.text = list[position].personName
        holder.lastMessageView.text = list[position].lastMessage
        holder.costView.text = list[position].cost.toString()+"$"
        val a: ArrayAdapter<String>
    }

    inner class ChatsHolder : RecyclerView.ViewHolder{
        var checkBox: CheckBox
        var avatarView: RoundedImageView
        var personNameView: TextView
        var lastMessageView: TextView
        var costView: TextView
        constructor(v: View) : super(v) {
            checkBox = v.findViewById(R.id.checkBox)
            avatarView = v.findViewById(R.id.avatar_view)
            personNameView = v.findViewById(R.id.person_name_view)
            lastMessageView = v.findViewById(R.id.last_message_view)
            costView = v.findViewById(R.id.cost_view)
        }
    }
}