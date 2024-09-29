package com.example.newsdispatcher.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.newsdispatcher.database.data.SearchHistory
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchHistoryDao {
    @Query("SELECT * FROM SearchHistory")
    fun getAll(): Flow<List<SearchHistory>>

    @Insert
    fun insert(newQuery: SearchHistory)
}