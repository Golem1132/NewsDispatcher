package com.example.newsdispatcher.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.newsdispatcher.model.Article
import java.util.UUID

@Entity
data class ArticleEntry(
    @PrimaryKey(autoGenerate = false)
    val id: String = UUID.randomUUID().toString(),
    val article: Article
)