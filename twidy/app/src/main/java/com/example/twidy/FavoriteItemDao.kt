package com.example.twidy

import androidx.room.*

@Dao
interface FavoriteItemDao {
    @Query("SELECT * FROM favoriteitem ORDER BY personName")
    suspend fun getAll(): List<FavoriteItem>
    @Query("SELECT * FROM favoriteitem WHERE id = :id")
    suspend fun getById(id: Int): FavoriteItem
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<FavoriteItem>)
    @Delete
    suspend fun delete(item: FavoriteItem)
}