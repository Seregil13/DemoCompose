package com.example.core.domain

data class PagedApiResponse<T>(
    val page: Int,
    val results: List<T>,
    val totalPages: Int,
    val totalResults: Int,
)
