package com.example.newsdispatcher.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.newsdispatcher.model.Article

@Entity
data class ArticleEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val article: Article
)
