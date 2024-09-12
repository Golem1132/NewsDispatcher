package com.example.newsdispatcher.api

import com.example.newsdispatcher.api.service.NewsService
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json

class NewsDispatcherClient {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    }

    fun getNewsService() = NewsService(client)
}