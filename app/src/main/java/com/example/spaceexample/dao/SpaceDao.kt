package com.example.spaceexample.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.spaceexample.models.FavoriteShip
import com.example.spaceexample.models.ShipModel

@Dao
interface SpaceDao {
    @Query("SELECT * FROM ships")
    fun getAll(): LiveData<List<ShipModel>>

    @Insert
    fun insert(ship: ShipModel)

    @Query("SELECT * FROM ships order by id DESC limit 1 ")
    fun getShip(): LiveData<ShipModel>

    @Delete
    fun delete(ship: ShipModel)


}