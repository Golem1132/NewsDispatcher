package com.example.newsdispatcher.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newsdispatcher.database.converter.ArticleConverter
import com.example.newsdispatcher.database.dao.ArticleEntryDao
import com.example.newsdispatcher.database.dao.NewsDispatcherRemoteKeysDao

@Database(
    entities = [
        ArticleEntry::class,
        NewsDispatcherRemoteKeys::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(ArticleConverter::class)
abstract class NewsDispatcherDatabase : RoomDatabase() {

    abstract fun getArticleEntryDao(): ArticleEntryDao
    abstract fun getNewsDispatcherRemoteKeysDao(): NewsDispatcherRemoteKeysDao

    companion object {
        @Volatile
        private var INSTANCE: NewsDispatcherDatabase? = null
        fun getInstance(context: Context): NewsDispatcherDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NewsDispatcherDatabase::class.java,
                    "NewsDispatcherDatabase.db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}