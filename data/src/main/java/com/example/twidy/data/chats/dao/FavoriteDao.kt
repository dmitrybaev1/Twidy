package com.example.twidy.data.chats.dao

import androidx.room.*
import com.example.twidy.data.chats.entities.FavoriteEntity

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favoriteentity")
    suspend fun getAll(): List<FavoriteEntity>
    @Query("SELECT * FROM favoriteentity WHERE id = :id")
    suspend fun getById(id: Int): FavoriteEntity
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<FavoriteEntity>)
    @Delete
    suspend fun delete(item: FavoriteEntity)
}