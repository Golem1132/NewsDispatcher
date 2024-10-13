package com.example.newsdispatcher.database.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.newsdispatcher.model.Article
import java.util.UUID

@Entity
data class SavedEntry(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val article: Article
)
