package com.example.twidy

import androidx.room.*

@Dao
interface ChatItemDao {
    @Query("SELECT * FROM chatitem")
    fun getAll(): List<ChatItem>
    @Query("SELECT * FROM chatitem WHERE id = :id")
    fun getById(id: Int): ChatItem
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<ChatItem>)
    @Delete
    fun delete(item: ChatItem)
}