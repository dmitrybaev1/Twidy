package com.example.twidy.data.chats.dao

import androidx.room.*
import com.example.twidy.ui.chats.items.FavoriteItem

@Dao
interface FavoriteItemDao {
    @Query("SELECT * FROM favoriteitem")
    suspend fun getAll(): List<FavoriteItem>
    @Query("SELECT * FROM favoriteitem WHERE id = :id")
    suspend fun getById(id: Int): FavoriteItem
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<FavoriteItem>)
    @Delete
    suspend fun delete(item: FavoriteItem)
}