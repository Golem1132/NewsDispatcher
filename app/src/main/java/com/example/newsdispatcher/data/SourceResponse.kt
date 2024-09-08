package com.example.newsdispatcher.data

import com.example.newsdispatcher.model.Source
import kotlinx.serialization.Serializable

@Serializable
data class SourceResponse(
    val status: String,
    val code: String? = null,
    val message: String? = null,
    val sources: Array<Source> = emptyArray()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SourceResponse

        if (status != other.status) return false
        if (code != other.code) return false
        if (message != other.message) return false
        if (!sources.contentEquals(other.sources)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = status.hashCode()
        result = 31 * result + (code?.hashCode() ?: 0)
        result = 31 * result + (message?.hashCode() ?: 0)
        result = 31 * result + sources.contentHashCode()
        return result
    }
}
