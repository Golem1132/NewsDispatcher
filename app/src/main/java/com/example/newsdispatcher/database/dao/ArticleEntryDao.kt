package com.example.newsdispatcher.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.newsdispatcher.database.ArticleEntry
import com.example.newsdispatcher.model.Article
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleEntryDao {

    @Query("SELECT article FROM ArticleEntry")
    fun getAllArticles(): List<Article>

    @Query("SELECT article FROM ArticleEntry")
    fun getAllArticlesAsFlow(): Flow<List<Article>>

    @Insert
    fun insertArticles(articleEntries: List<ArticleEntry>)

    @Query("DELETE FROM ArticleEntry")
    fun clearTable()
}