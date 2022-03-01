package com.example.spaceexample.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.spaceexample.models.FavoriteShip

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM favorite_ships")
    fun getAll(): LiveData<List<FavoriteShip>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(ship: FavoriteShip)


    @Delete
    fun delete(ship: FavoriteShip)


}