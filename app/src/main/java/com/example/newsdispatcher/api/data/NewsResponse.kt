package com.example.newsdispatcher.api.data

import com.example.newsdispatcher.model.Article
import kotlinx.serialization.Serializable

@Serializable
data class NewsResponse(
    val status: String,
    val code: String? = null,
    val message: String? = null,
    val totalResults: Int? = null,
    val articles: Array<Article> = emptyArray()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as NewsResponse

        if (status != other.status) return false
        if (code != other.code) return false
        if (message != other.message) return false
        if (totalResults != other.totalResults) return false
        if (!articles.contentEquals(other.articles)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = status.hashCode()
        result = 31 * result + (code?.hashCode() ?: 0)
        result = 31 * result + (message?.hashCode() ?: 0)
        result = 31 * result + (totalResults ?: 0)
        result = 31 * result + (articles.contentHashCode())
        return result
    }
}
