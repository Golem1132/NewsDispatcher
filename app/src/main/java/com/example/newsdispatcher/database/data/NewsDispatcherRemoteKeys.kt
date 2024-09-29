package com.example.newsdispatcher.database.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NewsDispatcherRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val prevKey: Int?,
    val nextKey: Int?
)
