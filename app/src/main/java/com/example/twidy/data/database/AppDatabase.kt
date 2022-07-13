package com.example.twidy.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.twidy.data.chats.dao.ChatItemDao
import com.example.twidy.data.chats.dao.FavoriteItemDao
import com.example.twidy.ui.chats.items.ChatItem
import com.example.twidy.ui.chats.items.FavoriteItem

@Database(entities = [ChatItem::class, FavoriteItem::class],version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun chatItemDao(): ChatItemDao
    abstract fun favoriteItemDao(): FavoriteItemDao
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}