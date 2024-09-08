package com.example.newsdispatcher.model

import kotlinx.serialization.Serializable

@Serializable
data class Source(
    val id: String? = null,
    val name: String,
    val description: String? = null,
    val url: String? = null,
    val category: String? = null,
    val language: String? = null,
    val country: String? = null
)
