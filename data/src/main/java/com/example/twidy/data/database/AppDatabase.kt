package com.example.twidy.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.twidy.data.chats.dao.ChatDao
import com.example.twidy.data.chats.dao.FavoriteDao
import com.example.twidy.data.chats.entities.ChatEntity
import com.example.twidy.data.chats.entities.FavoriteEntity

@Database(entities = [ChatEntity::class, FavoriteEntity::class],version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun chatDao(): ChatDao
    abstract fun favoriteDao(): FavoriteDao
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