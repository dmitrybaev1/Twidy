package com.example.twidy.ui.chats.vm

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.twidy.*
import com.example.twidy.api.MainAPI
import com.example.twidy.database.AppDatabase
import com.example.twidy.entities.ResultChatsData
import com.example.twidy.entities.ResultConfirmData
import com.example.twidy.entities.ResultFavoriteData
import com.example.twidy.ui.chats.items.ChatItem
import com.example.twidy.ui.chats.items.FavoriteItem
import com.example.twidy.utils.CryptLib
import com.example.twidy.utils.InternetChecker
import com.example.twidy.utils.RetrofitBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.crypto.BadPaddingException
import kotlin.coroutines.CoroutineContext

class ChatsViewModel(application: Application) : AndroidViewModel(application) {

    private val token = "7684b4103bfdd32b6fc3df63236a8f194e344601"

    val resultConfirmData = MutableLiveData<ResultConfirmData>()

    private val _chatsListLiveData = MutableLiveData<ArrayList<ChatItem>>()
    val chatsListLiveData: LiveData<ArrayList<ChatItem>> = _chatsListLiveData
    private var chatsList: ArrayList<ChatItem>? = null

    private val _favoriteListLiveData = MutableLiveData<ArrayList<FavoriteItem>>()
    val favoriteListLiveData: LiveData<ArrayList<FavoriteItem>> = _favoriteListLiveData
    private var favoriteList: ArrayList<FavoriteItem>? = null

    private val _error = MutableLiveData<Int>()
    val error: LiveData<Int> = _error

    private val _closePopupLiveData = MutableLiveData<Boolean>()
    val closePopupLiveData: LiveData<Boolean> = _closePopupLiveData

    private val _apiError = MutableLiveData<String>()
    val apiError: LiveData<String> = _apiError

    private val retrofit = RetrofitBuilder.build()
    private val job = Job()
    private val context: CoroutineContext
        get() = Dispatchers.Main + job
    private val vmScope = CoroutineScope(context)

    private val cryptLib = CryptLib()

    var isToolbarInEditMode = false

    fun closePopup(){
        _closePopupLiveData.value=true
    }
    private fun formChats(result: ResultChatsData){
        chatsList = ArrayList()
        for(item in result.items)
            chatsList!!.add(
                ChatItem(
                    item.id,
                    item.peer.image.toString(),
                    item.peer.name,
                    item.last_message.message,
                    item.last_message.timestamp,
                    0,
                    false,
                    false,
                    item.peer.type.toString()
                )
            )
        chatsList!!.sortByDescending { it.timestamp }
    }

    private fun formFavorite(result: ResultFavoriteData){
        favoriteList = ArrayList()
        for(item in result.listOf)
            favoriteList!!.add(
                FavoriteItem(
                    item.id,
                    item.photo,
                    item.firstName + " " + item.lastName,
                    item.biography,
                    item.video_call_price?.let { it > 0 } ?: false,
                    item.audio_call_price?.let { it > 0 } ?: false
                )
            )
        favoriteList!!.sortBy { it.personName }
    }
    fun sync(){
        getChats()
    }
    private fun decrypt(list: ArrayList<ChatItem>){
        for(i in list){
            try {
                i.lastMessage = cryptLib.decryptCipherTextWithRandomIV(i.lastMessage,cryptLib.sha1(i.id.toString()))
            }
            catch (t: BadPaddingException){
                Log.e("err","decrypt error in chat"+i.id.toString())
                //_error.value = R.string.decrypt_error
            }
        }
    }
    fun getChats(){
        vmScope.launch {
            val database = AppDatabase.getDatabase(getApplication())
            val localChatsList = database.chatItemDao().getAll() as ArrayList<ChatItem>
            decrypt(localChatsList)
            chatsList?.let {
                if(!(it.size==localChatsList.size&&it.containsAll(localChatsList))) {
                    chatsList = localChatsList
                    chatsList?.let{inner->
                        _chatsListLiveData.postValue(inner)
                    }
                }
            }?: run {
                chatsList = localChatsList
                chatsList?.let{
                    _chatsListLiveData.postValue(it)
                }
            }
            if(InternetChecker.isOnline(getApplication())) {
                val api = retrofit.create(MainAPI::class.java)
                try {
                    val chatsData = api.getChats(token)
                    if (chatsData.status == "ok") {
                        formChats(chatsData.result)
                        chatsList?.let{
                            if(!(it.size==localChatsList.size&&it.containsAll(localChatsList))) {
                                database.chatItemDao().insertAll(it)
                                decrypt(it)
                                _chatsListLiveData.postValue(it)
                            }
                        }

                    } else {
                        _apiError.postValue(chatsData.message)
                    }
                } catch (t: Throwable) {
                    _error.postValue(R.string.chats_error);
                }
            }
            else
                _error.postValue(R.string.no_internet)
        }
    }
    //МЕТОД С ВЫЗОВОМ API(вызвать user.getFavorite и сформировать объект юзера(FavoriteItem))
    fun getFavorite(type: String){
        //загружаем избранных юзеров
        vmScope.launch {
            val database = AppDatabase.getDatabase(getApplication())
            var localFavoriteList = database.favoriteItemDao().getAll() as ArrayList<FavoriteItem>
            favoriteList?.let {
                if(type=="video"){
                    favoriteList = it.filter { x-> x.isVideoAccepted } as ArrayList<FavoriteItem>
                    localFavoriteList = localFavoriteList.filter { x -> x.isVideoAccepted } as ArrayList<FavoriteItem>
                }
                else if(type=="audio"){
                    favoriteList = it.filter { x-> x.isAudioAccepted } as ArrayList<FavoriteItem>
                    localFavoriteList = localFavoriteList.filter { x -> x.isAudioAccepted } as ArrayList<FavoriteItem>
                }
                favoriteList = localFavoriteList
                favoriteList?.let{inner->
                    _favoriteListLiveData.postValue(inner)
                }
            }?: run {
                if(type=="video")
                    localFavoriteList = localFavoriteList.filter { x -> x.isVideoAccepted } as ArrayList<FavoriteItem>
                else if(type=="audio")
                    localFavoriteList = localFavoriteList.filter { x -> x.isAudioAccepted } as ArrayList<FavoriteItem>
                favoriteList = localFavoriteList
                favoriteList?.let{
                    _favoriteListLiveData.postValue(it)
                }
            }
            if (InternetChecker.isOnline(getApplication())) {
                val api = retrofit.create(MainAPI::class.java)
                try {
                    val favoriteData = api.getFavorite(token)
                    if (favoriteData.status == "ok") {
                        formFavorite(favoriteData.result)
                        favoriteList?.let{
                            if(type=="video"){
                                favoriteList = it.filter { x-> x.isVideoAccepted } as ArrayList<FavoriteItem>
                                localFavoriteList = localFavoriteList.filter { x -> x.isVideoAccepted } as ArrayList<FavoriteItem>
                            }
                            else if(type=="audio"){
                                favoriteList = it.filter { x-> x.isAudioAccepted } as ArrayList<FavoriteItem>
                                localFavoriteList = localFavoriteList.filter { x -> x.isAudioAccepted } as ArrayList<FavoriteItem>
                            }
                            _favoriteListLiveData.postValue(favoriteList)
                            if(type=="all")
                                database.favoriteItemDao().insertAll(it)
                        }
                    } else
                        _apiError.postValue(favoriteData.message)
                } catch (t: Throwable) {
                    _error.postValue(R.string.favorite_error)
                }
            }
            else
                _error.postValue(R.string.no_internet)
        }
    }
    fun searchFavorite(s: String){
        val searchList = favoriteList!!.filter { x -> x.personName.contains(s,true) } as ArrayList<FavoriteItem>
        if(searchList.containsAll(favoriteList!!))
            _favoriteListLiveData.value = favoriteList
        else
            _favoriteListLiveData.value = searchList
    }
    fun searchChats(s: String){
        val searchList = chatsList!!.filter { x -> x.name.contains(s,true) } as ArrayList<ChatItem>
        _chatsListLiveData.value = searchList
    }
    //По хорошему надо проверять статус api.archive, соответственно надо переделать возвращаемое значение с Unit на ArchiveData
    fun archiveChats(){
        vmScope.launch {
            if(InternetChecker.isOnline(getApplication())) {
                val arcList = chatsList!!.filter { x -> x.checked } as ArrayList<ChatItem>
                var s = ""
                if(arcList.size==0) {
                    _error.postValue(R.string.choose_chats)
                    return@launch
                }
                if (arcList.size > 1) {
                    for (i in arcList.indices) {
                        s += if (arcList[i] == arcList[arcList.size - 1])
                            arcList[i].id.toString()
                        else
                            arcList[i].id.toString() + ","
                    }
                    val api = retrofit.create(MainAPI::class.java)
                    try {
                        api.archive(token, s)
                        chatsList = chatsList!!.filter { x -> !x.checked } as ArrayList<ChatItem>
                        _chatsListLiveData.postValue(chatsList)
                        sync()
                    } catch (t: Throwable) {
                        _error.postValue(R.string.archive_error)
                    }
                } else if(arcList.size==1) {
                    val api = retrofit.create(MainAPI::class.java)
                    try {
                        api.archive(token, arcList[0].id)
                        chatsList = chatsList!!.filter { x -> !x.checked } as ArrayList<ChatItem>
                        _chatsListLiveData.postValue(chatsList)
                        sync()
                    } catch (t: Throwable) {
                        _error.postValue(R.string.archive_error)
                    }
                }
            }
            else//можно кэшировать архивированные чаты, и при возобновлении интернета вызывать archive к серверу
                _error.postValue(R.string.no_internet)

        }
    }

    fun checkAllChats(){
        chatsList!!.forEach { x -> x.checked=true }
        _chatsListLiveData.value = chatsList
    }
    fun toSourceListChats(){
        _chatsListLiveData.value = chatsList
    }
    fun toSourceListFavorite(){
        _favoriteListLiveData.value = favoriteList
    }
}