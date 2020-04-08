package com.example.twidy.ui.chats

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.twidy.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.ConnectException
import kotlin.coroutines.CoroutineContext

class ChatsViewModel : ViewModel() {

    val resultConfirmData = MutableLiveData<ResultConfirmData>()

    private val _chatsListLiveData = MutableLiveData<ArrayList<ChatItem>>()
    val chatsListLiveData: LiveData<ArrayList<ChatItem>> = _chatsListLiveData
    private lateinit var chatsList: ArrayList<ChatItem>

    private val _favoriteListLiveData = MutableLiveData<ArrayList<FavoriteItem>>()
    val favoriteListLiveData: LiveData<ArrayList<FavoriteItem>> = _favoriteListLiveData
    private lateinit var favoriteList: ArrayList<FavoriteItem>

    private val _error = MutableLiveData<Int>()
    val error: LiveData<Int> = _error

    private val _apiError = MutableLiveData<String>()
    val apiError: LiveData<String> = _apiError

    private lateinit var chatsData: ChatsData

    private val retrofit = RetrofitBuilder.build()
    private val job = Job()
    private val context: CoroutineContext
        get() = Dispatchers.Main + job
    private val vmScope = CoroutineScope(context)
    //МЕТОД С ВЫЗОВОМ API(chat.getLists и chat.getMessages, после чего формировать объект чата(ChatItem))
    fun getChats(){
        //загружаем чаты
        /*vmScope.launch {
            val api = retrofit.create(MainAPI::class.java)
            try {
                chatsData = api.getChats("")
                if(chatsData.status=="ok") {
                    //формировать лист чатов
                    _chatsListLiveData.postValue(chatsList)
                }
                else {
                    _apiError.postValue(chatsData.message)
                }
            }
            catch (ex: ConnectException){
                _error.postValue(R.string.host_error)
            }
            catch (ex: HttpException){
                _error.postValue(R.string.chats_error)
            }
        }*/
        chatsList = ArrayList()
        chatsList.add(ChatItem(0,"","Name Surname","Last message",1,false,false,"Chat"))
        chatsList.add(ChatItem(1,"","Name Surname","Last message",3,false,false,"Chat"))
        chatsList.add(ChatItem(2,"","Name Surname","Last message",0,false,false,"Chat"))
        chatsList.add(ChatItem(3,"","Name Surname","Last message",1,false,false,"Chat"))
        chatsList.add(ChatItem(4,"","Channel Name","Last message",15,false,false,"Channel"))
        chatsList.add(ChatItem(5,"","Name Surname","Last message",0,false,false,"Chat"))
        _chatsListLiveData.value = chatsList
    }
    //МЕТОД С ВЫЗОВОМ API(вызвать user.getFavorite и сформировать объект юзера(FavoriteItem))
    fun getFavoriteAll(){
        //загружаем избранных юзеров
        favoriteList = ArrayList()
        favoriteList.add(FavoriteItem(0,"","Favorite All","Description",true,true))
        favoriteList.add(FavoriteItem(1,"","Favorite All","Description",true,true))
        favoriteList.add(FavoriteItem(2,"","Favorite All","Description",true,true))
        _favoriteListLiveData.value = favoriteList
    }
    //тоже что и выше, но только избранные юзеры с разрешенным видео
    fun getFavoriteVideoAccepted(){
        //загружаем избранных юзеров, у кого разрешено видео
        favoriteList = ArrayList()
        favoriteList.add(FavoriteItem(0,"","Favorite Video","Description",true,true))
        favoriteList.add(FavoriteItem(1,"","Favorite Video","Description",true,true))
        favoriteList.add(FavoriteItem(2,"","Favorite Video","Description",true,true))
        _favoriteListLiveData.value = favoriteList
    }
    //тоже что и выше, но только избранные юзеры с разрешенным аудио
    fun getFavoriteAudioAccepted(){
        //загружаем избранных юзеров, у кого разрешено аудио
        favoriteList = ArrayList()
        favoriteList.add(FavoriteItem(0,"","Favorite Audio","Description",true,true))
        favoriteList.add(FavoriteItem(1,"","Favorite Audio","Description",true,true))
        favoriteList.add(FavoriteItem(2,"","Favorite Audio","Description",true,true))
        _favoriteListLiveData.value = favoriteList
    }
    fun searchFavorite(s: String){
        val searchList = favoriteList.filter { x -> x.personName.contains(s,true) } as ArrayList<FavoriteItem>
        if(searchList.containsAll(favoriteList))
            _favoriteListLiveData.value = favoriteList
        else
            _favoriteListLiveData.value = searchList
    }
    fun searchChats(s: String){
        val searchList = chatsList.filter { x -> x.name.contains(s,true) } as ArrayList<ChatItem>
        _chatsListLiveData.value = searchList
    }
    //МЕТОД С ВЫЗОВОМ API (chats.archive)
    fun archiveChats(){
        chatsList = chatsList.filter { x -> !x.checked } as ArrayList<ChatItem>
        _chatsListLiveData.value = chatsList
    }
    fun checkAllChats(){
        chatsList.forEach { x -> x.checked=true }
        _chatsListLiveData.value = chatsList
    }
    fun toSourceListChats(){
        _chatsListLiveData.value = chatsList
    }
    fun toSourceListFavorite(){
        _favoriteListLiveData.value = favoriteList
    }
}