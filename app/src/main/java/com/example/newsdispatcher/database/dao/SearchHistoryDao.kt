package com.example.newsdispatcher.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.newsdispatcher.database.data.SearchHistory
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchHistoryDao {
    @Query("SELECT * FROM SearchHistory")
    fun getAll(): Flow<List<SearchHistory>>

    @Query("SELECT * FROM SearchHistory WHERE `query` LIKE LOWER(:query)")
    fun find(query: String): SearchHistory?

    @Delete
    fun delete(searchHistory: SearchHistory)

    @Insert
    fun put(searchHistory: SearchHistory)

    @Transaction
    fun insert(newQuery: SearchHistory) {
        val foundQuery = find(newQuery.query)
        if (foundQuery != null)
            delete(foundQuery)
        put(newQuery)
    }
}