package com.example.twidy

import androidx.room.*

@Dao
interface FavoriteItemDao {
    @Query("SELECT * FROM favoriteitem")
    fun getAll(): List<FavoriteItem>
    @Query("SELECT * FROM favoriteitem WHERE id = :id")
    fun getById(id: Int): FavoriteItem
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<FavoriteItem>)
    @Delete
    fun delete(item: FavoriteItem)
}