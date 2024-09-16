package com.example.newsdispatcher

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.newsdispatcher.api.service.NewsService
import com.example.newsdispatcher.model.Article

class NewsDispatcherPagingSource(
    private val api: NewsService
) : PagingSource<Int, Article>() {
    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val nextPageNumber = params.key ?: 1
        var loadResult: LoadResult<Int, Article>? = null
        api.getTopHeadlines(null, 10, nextPageNumber)
            .onSuccess {
                loadResult = LoadResult.Page(
                    it.articles.toList(),
                    null,
                    nextPageNumber
                )
            }
            .onError {
                loadResult = LoadResult.Error(it)
            }
        return loadResult!!
    }

}