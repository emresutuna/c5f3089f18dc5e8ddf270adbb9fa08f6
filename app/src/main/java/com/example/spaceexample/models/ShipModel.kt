package com.example.spaceexample.models

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "ships")
data class ShipModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id: Int?,
    @ColumnInfo(name = "name")
    var name: String?,
    @ColumnInfo(name = "strength")
    var strength: Int?,
    @ColumnInfo(name = "speed")
    var speed: Int?,
    @ColumnInfo(name = "capacity")
    var capacity: Int?
) {
}