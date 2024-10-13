package com.example.newsdispatcher.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.newsdispatcher.database.data.SavedEntry
import com.example.newsdispatcher.model.Article

@Dao
interface SavedEntryDao {
    @Query("SELECT article FROM SavedEntry")
    suspend fun getAll(): List<Article>

    @Query("SELECT article FROM SavedEntry WHERE article = :searchedArticle")
    suspend fun find(searchedArticle: Article): Article?

    @Insert
    suspend fun insert(newEntry: SavedEntry)

    @Query("DELETE FROM SavedEntry WHERE article = :savedArticle")
    suspend fun delete(savedArticle: Article)
}