package com.example.newsdispatcher.model

import kotlinx.serialization.Serializable

@Serializable
data class Source(
    val id: String?,
    val name: String,
    val description: String?,
    val url: String?,
    val category: String?,
    val language: String?,
    val country: String?
)