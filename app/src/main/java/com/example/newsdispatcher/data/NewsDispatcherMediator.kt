package com.example.newsdispatcher.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.newsdispatcher.api.data.NewsResponse
import com.example.newsdispatcher.database.NewsDispatcherDatabase
import com.example.newsdispatcher.database.data.ArticleEntry
import com.example.newsdispatcher.database.data.NewsDispatcherRemoteKeys

@OptIn(ExperimentalPagingApi::class)
class NewsDispatcherMediator(
    private val category: String?,
    private val api: suspend (Int) -> NewsResponse,
    private val db: NewsDispatcherDatabase
) : RemoteMediator<Int, ArticleEntry>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ArticleEntry>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKey = state.anchorPosition?.let { position ->
                        state.closestItemToPosition(position)?.id?.let { id ->
                            db.getNewsDispatcherRemoteKeysDao().getRemoteKeys(id)
                        }
                    }
                    remoteKey?.nextKey?.minus(1) ?: 1
                }

                LoadType.PREPEND -> {
                    val remoteKey = state.pages.firstOrNull {
                        it.data.isNotEmpty()
                    }?.data?.firstOrNull()?.let { article ->
                        db.getNewsDispatcherRemoteKeysDao().getRemoteKeys(article.id)
                        //getRemoteKey(article.id)
                    }
                    val prevPage = remoteKey?.prevKey ?: return MediatorResult.Success(
                        remoteKey != null
                    )
                    prevPage
                }

                LoadType.APPEND -> {
                    val remoteKey =
                        state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
                            ?.let { article ->
                                db.getNewsDispatcherRemoteKeysDao().getRemoteKeys(article.id)
                                //getRemoteKey(article.id)

                            }
                    val nextPage = remoteKey?.nextKey ?: return MediatorResult.Success(
                        remoteKey != null
                    )
                    nextPage
                }
            }
            val response = api(page)
            val endOfPaginationReached = response.articles.isEmpty()
            val prevPage = if (page == 1) null else page - 1
            val nextPage = if (endOfPaginationReached) null else page + 1
            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    println(response.status)
                    if (response.status.equals("ok", true)) {
                        db.getArticleEntryDao().deleteByCategory(category)
                        db.getNewsDispatcherRemoteKeysDao().deleteAllKeys()
                    }
                }
                val articleEntries = response.articles.map { article ->
                    ArticleEntry(
                        categoryId = category,
                        article = article
                    )
                }
                val keys = articleEntries.map {
                    NewsDispatcherRemoteKeys(
                        id = it.id,
                        prevPage,
                        nextPage
                    )
                }
                db.getArticleEntryDao().insertArticles(articleEntries)
                db.getNewsDispatcherRemoteKeysDao().addAllRemoteKeys(keys)
            }
            MediatorResult.Success(endOfPaginationReached)

        } catch (e: Exception) {
            e.printStackTrace()
            MediatorResult.Error(e)
        }
    }
}