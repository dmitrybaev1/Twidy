package com.example.twidy.ui.chats

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.twidy.*
import com.example.twidy.data.api.Failure
import com.example.twidy.data.api.NetworkFailure
import com.example.twidy.data.api.Success
import com.example.twidy.data.entities.*
import com.example.twidy.domain.ArchiveChatsUseCase
import com.example.twidy.domain.GetChatsUseCase
import com.example.twidy.domain.GetFavoritesUseCase
import com.example.twidy.data.chats.entities.ChatItem
import com.example.twidy.data.chats.entities.FavoriteItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class ChatsViewModel : ViewModel() {

    @Inject
    lateinit var getChatsUseCase: GetChatsUseCase

    @Inject
    lateinit var getFavoritesUseCase: GetFavoritesUseCase

    @Inject
    lateinit var archiveChatsUseCase: ArchiveChatsUseCase

    lateinit var resultConfirmData: ResultConfirmData

    private val _chatsListLiveData = MutableLiveData<List<ChatItem>>()
    val chatsListLiveData: LiveData<List<ChatItem>> = _chatsListLiveData
    private var chatsList: ArrayList<ChatItem> = arrayListOf()

    private val _favoriteListLiveData = MutableLiveData<List<FavoriteItem>>()
    val favoriteListLiveData: LiveData<List<FavoriteItem>> = _favoriteListLiveData
    private var favoriteList: ArrayList<FavoriteItem> = arrayListOf()

    private val _error = MutableLiveData<Int>()
    val error: LiveData<Int> = _error

    private val _closePopupLiveData = MutableLiveData<Boolean>()
    val closePopupLiveData: LiveData<Boolean> = _closePopupLiveData

    private val _apiError = MutableLiveData<String>()
    val apiError: LiveData<String> = _apiError

    var isToolbarInEditMode = false

    fun closePopup(){
        _closePopupLiveData.value=true
    }

    private fun sync(){
        fetchChats()
    }

    fun fetchChats(){
        viewModelScope.launch {
            getChatsUseCase(resultConfirmData.access_token).collect{result ->
                when(result){
                    is Success<List<ChatItem>> -> {
                        chatsList = result.data as ArrayList<ChatItem>
                        chatsList.sortByDescending { it.timestamp }
                        _chatsListLiveData.value = chatsList
                        if(!result.isRemote)
                            _error.value = R.string.no_internet
                    }
                    is Failure -> _apiError.value = result.message
                    is NetworkFailure -> _error.value = R.string.chats_error
                }
            }
        }
    }

    //загрузка избранных юзеров
    fun fetchFavorites(type: FavoritesType) {
        viewModelScope.launch {
            when (val result = getFavoritesUseCase(resultConfirmData.access_token)) {
                is Success<List<FavoriteItem>> -> {
                    favoriteList = result.data as ArrayList<FavoriteItem>
                    if (type == FavoritesType.VIDEO)
                        favoriteList =
                            favoriteList.filter { x -> x.isVideoAccepted } as ArrayList<FavoriteItem>
                    else if (type == FavoritesType.AUDIO)
                        favoriteList =
                            favoriteList.filter { x -> x.isAudioAccepted } as ArrayList<FavoriteItem>
                    favoriteList.sortBy { it.personName }
                    _favoriteListLiveData.value = favoriteList
                    if (!result.isRemote)
                        _error.value = R.string.no_internet
                }
                is Failure -> _apiError.value = result.message
                is NetworkFailure -> _error.value = R.string.favorite_error
            }
        }
    }

    //переделать возвращаемое значение с Unit на ArchiveData
    fun archiveChats(){
        viewModelScope.launch {
            val arcList = chatsList.filter { x -> x.checked }
            if(arcList.isEmpty()) {
                _error.value = R.string.choose_chats
                return@launch
            }
            when(archiveChatsUseCase(resultConfirmData.access_token,arcList)){
                is Success<String> -> sync()
                else -> _error.value = R.string.archive_error
            }
        }
    }

    fun searchFavorite(s: String){
        val searchList = favoriteList.filter { x -> x.personName.contains(s,true) }
        if(searchList.containsAll(favoriteList))
            _favoriteListLiveData.value = favoriteList
        else
            _favoriteListLiveData.value = searchList
    }

    fun searchChats(s: String){
        val searchList = chatsList.filter { x -> x.name.contains(s,true) }
        _chatsListLiveData.value = searchList
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