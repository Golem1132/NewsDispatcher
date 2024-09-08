package com.example.newsdispatcher.api

import com.example.newsdispatcher.BuildConfig
import com.example.newsdispatcher.data.NewsResponse
import com.example.newsdispatcher.data.SourceResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.get
import io.ktor.http.URLProtocol
import io.ktor.http.path
import io.ktor.serialization.kotlinx.json.json

class NewsServiceImp(
    private val client: HttpClient
) : NewsService {


    companion object {
        fun create() = NewsServiceImp(
            client = HttpClient(CIO) {
                install(io.ktor.client.plugins.contentnegotiation.ContentNegotiation) {
                    json()
                }
            }
        )
    }

    override suspend fun getSources(
        category: String?,
        language: String?,
        country: String?
    ): SourceResponse {
        return client.get {
            url {
                protocol = URLProtocol.HTTPS
                host = NewsHttpRoutes.BASE_URL
                path(NewsHttpRoutes.SOURCES)
                parameters.append(
                    "apikey",
                    BuildConfig.API_KEY
                )
                if (category != null)
                    parameters.append(
                        "category",
                        category
                    )
                if (language != null)
                    parameters.append(
                        "language",
                        language
                    )
                if (country != null)
                    parameters.append(
                        "country",
                        country
                    )
            }
        }.body()
    }

    override suspend fun getTopHeadlines(
        category: String?,
        pageSize: Int,
        page: Int
    ): NewsResponse {
        return client.get {
            url {
                protocol = URLProtocol.HTTPS
                host = NewsHttpRoutes.BASE_URL
                path(NewsHttpRoutes.TOP_HEADLINES)
                parameters.append(
                    "apikey",
                    BuildConfig.API_KEY
                )
                parameters.append(
                    "country",
                    "us"
                )
                if (category != null)
                    parameters.append("category", category)
                parameters.append("pageSize", pageSize.toString())
                parameters.append("page", page.toString())
            }
        }.body()
    }
}