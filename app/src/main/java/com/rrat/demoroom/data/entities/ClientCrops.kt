package com.rrat.demoroom.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "client_crops_table")
data class ClientCrops(
    @ColumnInfo(name = "client") val client: String,
    @ColumnInfo(name = "crop") val crop: String,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)