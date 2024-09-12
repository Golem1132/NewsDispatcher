package com.example.newsdispatcher.database.converter

import androidx.room.TypeConverter
import com.example.newsdispatcher.model.Article
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ArticleConverter {
    @TypeConverter
    fun fromArticleToJson(article: Article): String {
        return Json.encodeToString(article)
    }

    @TypeConverter
    fun fromJsonToArticle(json: String): Article {
        return Json.decodeFromString(json)
    }
}