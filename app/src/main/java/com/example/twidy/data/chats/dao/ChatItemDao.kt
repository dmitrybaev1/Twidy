package com.example.twidy.data.chats.dao

import androidx.room.*
import com.example.twidy.data.chats.entities.ChatItem


@Dao
interface ChatItemDao {
    @Query("SELECT * FROM chatitem")
    suspend fun getAll(): List<ChatItem>
    @Query("SELECT * FROM chatitem WHERE id = :id")
    suspend fun getById(id: Int): ChatItem
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<ChatItem>)
    @Delete
    suspend fun delete(item: ChatItem)
}