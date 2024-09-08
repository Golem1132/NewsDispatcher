package com.example.newsdispatcher.api

import com.example.newsdispatcher.data.NewsResponse
import com.example.newsdispatcher.data.SourceResponse

interface NewsService {

/*    suspend fun getEverything(
        apiKey: String
    )*/


    suspend fun getSources(
        category: String? = null,
        language: String? = null,
        country: String? = null
    ): SourceResponse

    suspend fun getTopHeadlines(
        category: String?,
        pageSize: Int,
        page: Int
    ): NewsResponse

}