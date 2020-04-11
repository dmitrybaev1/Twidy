package com.example.twidy

import androidx.room.*

@Dao
interface ChatItemDao {
    @Query("SELECT * FROM chatitem ORDER BY timestamp DESC")
    suspend fun getAll(): List<ChatItem>
    @Query("SELECT * FROM chatitem WHERE id = :id")
    suspend fun getById(id: Int): ChatItem
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<ChatItem>)
    @Delete
    suspend fun delete(item: ChatItem)
}