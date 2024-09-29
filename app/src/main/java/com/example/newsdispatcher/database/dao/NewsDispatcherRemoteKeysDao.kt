package com.example.newsdispatcher.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsdispatcher.database.data.NewsDispatcherRemoteKeys

@Dao
interface NewsDispatcherRemoteKeysDao {

    @Query("SELECT * FROM NewsDispatcherRemoteKeys WHERE id=:id")
    suspend fun getRemoteKeys(id: String): NewsDispatcherRemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(remoteKeys: List<NewsDispatcherRemoteKeys>)

    @Query("DELETE FROM NewsDispatcherRemoteKeys")
    suspend fun deleteAllKeys()
}