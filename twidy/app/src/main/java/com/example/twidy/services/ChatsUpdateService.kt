package com.example.twidy.services

import android.app.IntentService
import android.app.PendingIntent
import android.content.Intent
import android.util.Log
import com.example.twidy.api.MainAPI
import com.example.twidy.database.AppDatabase
import com.example.twidy.entities.Chat
import com.example.twidy.ui.chats.items.ChatItem
import com.example.twidy.utils.InternetChecker
import com.example.twidy.utils.RetrofitBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class ChatsUpdateService : IntentService("IntentService") {

    private var isActive = true
    private val retrofit = RetrofitBuilder.build()
    private lateinit var database: AppDatabase
    private lateinit var pendingIntent: PendingIntent


    override fun onHandleIntent(intent: Intent?) {
        val token = intent!!.getStringExtra("token")!!
        pendingIntent = intent.getParcelableExtra("pi")!!
            while (isActive) {
            loadChats(token)
            Log.d("service","service is working")
            Thread.sleep(5000)
        }
    }
    private fun loadChats(token: String){
        if(InternetChecker.isOnline(application)) {
            database = AppDatabase.getDatabase(application)
            val api = retrofit.create(MainAPI::class.java)
            try {
                val chatsDataExec = api.getChats(token).execute()
                val chatsData = chatsDataExec.body()!!
                if (chatsData.status == "ok") {
                    var oldList = database.chatItemDao().s_getAll()
                    var newList = formChats(chatsData.result.items)
                    if(!(oldList.containsAll(newList)&&newList.containsAll(oldList))) {
                        database.chatItemDao().insertAll(newList)
                        pendingIntent.send()
                    }
                }
            } catch (t: Throwable) {
                Log.e("t",t.message!!)
            }
        }
    }
    private fun formChats(items: ArrayList<Chat>):ArrayList<ChatItem>{
        val chatsList = ArrayList<ChatItem>()
        for(item in items)
            chatsList.add(
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
        chatsList.sortByDescending { it.timestamp }
        return chatsList
    }
    override fun onDestroy() {
        super.onDestroy()
        isActive=false
    }
}