package com.example.twidy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.github.siyamed.shapeimageview.RoundedImageView

class ChatsAdapter(var list: ArrayList<ChatItem>,var activity: MainActivity) : RecyclerView.Adapter<ChatsAdapter.ChatsHolder>(), View.OnLongClickListener {
    //private var checkedMode: Boolean = false
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatsHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.chat_item,parent,false)
        view.setOnLongClickListener(this)
        return ChatsHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ChatsHolder, position: Int) {
        holder.personNameView.text = list[position].personName
        holder.lastMessageView.text = list[position].lastMessage
        holder.costView.text = list[position].cost.toString()+"$"
        if(list[position].inCheckedMode)
            holder.checkBox.visibility = View.VISIBLE
        else
            holder.checkBox.visibility = View.GONE
        holder.checkBox.isChecked = list[position].checked
        var listener = View.OnClickListener{
            if(activity.isInEditModeChats) {
                list[position].checked = !list[position].checked
                notifyItemChanged(position)
            }
        }
        holder.view.setOnClickListener(listener)
        holder.checkBox.setOnClickListener(listener)
    }

    inner class ChatsHolder : RecyclerView.ViewHolder{
        var view: View
        var checkBox: CheckBox
        var avatarView: RoundedImageView
        var personNameView: TextView
        var lastMessageView: TextView
        var costView: TextView
        constructor(v: View) : super(v) {
            view = v
            checkBox = v.findViewById(R.id.checkBox)
            avatarView = v.findViewById(R.id.avatar_view)
            personNameView = v.findViewById(R.id.person_name_view)
            lastMessageView = v.findViewById(R.id.last_message_view)
            costView = v.findViewById(R.id.cost_view)
        }
    }

    fun changeMode(){
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
        }
    }
    override fun onLongClick(p0: View?): Boolean {
        changeMode()
        return true
    }

}