package com.example.twidy.ui.chats

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.twidy.ChatItem
import com.example.twidy.ChatsAdapter
import com.example.twidy.ResultConfirmData

class ChatsViewModel : ViewModel() {

    val resultConfirmData = MutableLiveData<ResultConfirmData>()

    private val _chatsAdapter = MutableLiveData<ChatsAdapter>()
    val chatsAdapter: LiveData<ChatsAdapter> = _chatsAdapter

    private val list: ArrayList<ChatItem> = ArrayList()
    private lateinit var searchList: ArrayList<ChatItem>
    fun loadChats(){
        //загружаем чаты
        list.add(ChatItem("","Dmitry Baev","Hello my friend!",200))
        list.add(ChatItem("","Nikita Boiv","AAAAmatebalsukablyaaaaaa",20))
        list.add(ChatItem("","Dmitry Baev","Hello my friend!",200))
        list.add(ChatItem("","Nikita Boiv","AAAA mat ebal suka blyaaaaaa",20))
        list.add(ChatItem("","Dmitry Baev","Hello my friend!",200))
        list.add(ChatItem("","Nikita Boiv","AAAA mat ebal suka blyaaaaaa",20))
        list.add(ChatItem("","Dmitry Baev","Hello my friend!",200))
        list.add(ChatItem("","Nikita Boiv","AAAA mat ebal suka blyaaaaaa",20))
        list.add(ChatItem("","Dmitry Baev","Hello my friend!",200))
        list.add(ChatItem("","Nikita Boiv","AAAA mat ebal suka blyaaaaaa",20))
        list.add(ChatItem("","Dmitry Baev","Hello my friend!",200))
        list.add(ChatItem("","Nikita Boiv","AAAA mat ebal suka blyaaaaaa",20))
        list.add(ChatItem("","Dmitry Baev","Hello my friend!",200))
        list.add(ChatItem("","Nikita Boiv","AAAA mat ebal suka blyaaaaaa",20))
        _chatsAdapter.value = ChatsAdapter(list)
    }
    fun search(s: String){
        searchList = list.filter { x -> x.personName.contains(s,true) } as ArrayList<ChatItem>
        _chatsAdapter.value = ChatsAdapter(searchList)
    }
    fun toSourceList(){
        _chatsAdapter.value = ChatsAdapter(list)
    }
}