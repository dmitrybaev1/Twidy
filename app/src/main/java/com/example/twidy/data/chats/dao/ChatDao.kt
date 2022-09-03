package com.example.twidy.data.chats.dao

import androidx.room.*
import com.example.twidy.data.chats.entities.ChatEntity
import com.example.twidy.ui.chats.entities.ChatItem


@Dao
interface ChatDao {
    @Query("SELECT * FROM chatentity")
    suspend fun getAll(): List<ChatEntity>
    @Query("SELECT * FROM chatentity WHERE id = :id")
    suspend fun getById(id: Int): ChatEntity
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<ChatEntity>)
    @Delete
    suspend fun delete(item: ChatEntity)
}