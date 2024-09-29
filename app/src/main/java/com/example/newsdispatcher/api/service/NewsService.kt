package com.example.newsdispatcher.api.service

import com.example.newsdispatcher.BuildConfig
import com.example.newsdispatcher.api.ApiOperation
import com.example.newsdispatcher.api.NewsHttpRoutes
import com.example.newsdispatcher.api.data.NewsResponse
import com.example.newsdispatcher.api.data.SourceResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.URLProtocol
import io.ktor.http.path

class NewsService(
    private val client: HttpClient
) {
    suspend fun getSources(
        category: String?,
        language: String?,
        country: String?
    ): ApiOperation<SourceResponse> {
        return try {
            ApiOperation.Success(
                client.get {
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
            )
        } catch (e: Exception) {
            ApiOperation.Error(e)
        }
    }

    suspend fun getTopHeadlines(
        category: String?,
        pageSize: Int,
        page: Int
    ): ApiOperation<NewsResponse> {
        return try {
            ApiOperation.Success(
                client.get {
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
            )
        } catch (e: Exception) {
            ApiOperation.Error(e)
        }
    }

    suspend fun getTopHeadlinesPaged(
        category: String,
        pageSize: Int,
        page: Int
    ): NewsResponse {
        return try {
            client.get {
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
                    parameters.append("category", category)
                    parameters.append("pageSize", pageSize.toString())
                    parameters.append("page", page.toString())
                }
            }.body()

        } catch (e: Exception) {
            NewsResponse(
                status = "Error",
                code = "500",

                )
        }
    }

    suspend fun getEverything(
        pageSize: Int,
        page: Int
    ): NewsResponse {
        return try {
            client.get {
                url {
                    protocol = URLProtocol.HTTPS
                    host = NewsHttpRoutes.BASE_URL
                    path("v2/everything")
                    parameters.append(
                        "apikey",
                        BuildConfig.API_KEY
                    )
                    parameters.append(
                        "q",
                        "bitcoin"
                    )
                    parameters.append("pageSize", pageSize.toString())
                    parameters.append("page", page.toString())
                }
            }.body()

        } catch (e: Exception) {
            NewsResponse(
                status = "Error",
                code = "500"
            )
        }
    }
}