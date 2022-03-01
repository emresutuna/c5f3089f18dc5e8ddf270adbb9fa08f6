package com.example.spaceexample.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_ships")
class FavoriteShip(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int?,
    ) : Ships() {

}
