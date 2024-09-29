package com.example.newsdispatcher.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.newsdispatcher.database.data.ArticleEntry
import com.example.newsdispatcher.model.Article
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleEntryDao {

    @Query("SELECT * FROM ArticleEntry WHERE categoryId =:currentCategory")
    fun getAllArticlesByCategory(currentCategory: String): PagingSource<Int, ArticleEntry>

    @Query("SELECT article FROM ArticleEntry")
    fun getAllArticlesAsFlow(): Flow<List<Article>>

    @Insert
    fun insertArticles(articleEntries: List<ArticleEntry>)

    @Query("DELETE FROM ArticleEntry WHERE categoryId=:currentCategory")
    fun deleteByCategory(currentCategory: String)

    @Query("DELETE FROM ArticleEntry")
    fun clearTable()
}