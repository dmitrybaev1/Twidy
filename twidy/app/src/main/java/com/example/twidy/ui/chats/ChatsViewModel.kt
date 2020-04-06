package com.example.twidy.ui.chats

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.twidy.ChatItem
import com.example.twidy.ChatsAdapter
import com.example.twidy.ResultConfirmData

class ChatsViewModel : ViewModel() {

    val resultConfirmData = MutableLiveData<ResultConfirmData>()

    private val _listLiveData = MutableLiveData<ArrayList<ChatItem>>()
    val listLiveData: LiveData<ArrayList<ChatItem>> = _listLiveData

    private lateinit var list: ArrayList<ChatItem>
    private lateinit var searchList: ArrayList<ChatItem>
    fun loadChats(){
        //загружаем чаты
        list = ArrayList()
        list.add(ChatItem("","Dmitry Baev","Hello my friend!",200,false,false))
        list.add(ChatItem("","Nikita Boiv","AAAAmatebalsukablyaaaaaa",20,false,false))
        list.add(ChatItem("","Dmitry Baev","Hello my friend!",200,false,false))
        list.add(ChatItem("","Nikita Boiv","AAAA mat ebal suka blyaaaaaa",20,false,false))
        list.add(ChatItem("","Dmitry Baev","Hello my friend!",200,false,false))
        list.add(ChatItem("","Nikita Boiv","AAAA mat ebal suka blyaaaaaa",20,false,false))
        list.add(ChatItem("","Dmitry Baev","Hello my friend!",200,false,false))
        list.add(ChatItem("","Nikita Boiv","AAAA mat ebal suka blyaaaaaa",20,false,false))
        list.add(ChatItem("","Dmitry Baev","Hello my friend!",200,false,false))
        list.add(ChatItem("","Nikita Boiv","AAAA mat ebal suka blyaaaaaa",20,false,false))
        list.add(ChatItem("","Dmitry Baev","Hello my friend!",200,false,false))
        list.add(ChatItem("","Nikita Boiv","AAAA mat ebal suka blyaaaaaa",20,false,false))
        list.add(ChatItem("","Dmitry Baev","Hello my friend!",200,false,false))
        list.add(ChatItem("","Nikita Boiv","AAAA mat ebal suka blyaaaaaa",20,false,false))
        _listLiveData.value = list
    }
    fun search(s: String){
        searchList = list.filter { x -> x.personName.contains(s,true) } as ArrayList<ChatItem>
        _listLiveData.value = searchList
    }
    fun delete(){
        list = list.filter { x -> !x.checked } as ArrayList<ChatItem>
        _listLiveData.value = list
    }
    fun archive(){
        list = list.filter { x -> !x.checked } as ArrayList<ChatItem>
        _listLiveData.value = list
    }
    fun checkAll(){
        list.forEach { x -> x.checked=true }
        _listLiveData.value = list
    }
    fun toSourceList(){
        _listLiveData.value = list
    }
}