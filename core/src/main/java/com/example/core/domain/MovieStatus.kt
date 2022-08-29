package com.example.core.domain

enum class MovieStatus {
    RUMORED, PLANNED, IN_PRODUCTION, POST_PRODUCTION, RELEASED, CANCELED;

    companion object {
        fun fromString(string: String?): MovieStatus = when (string) {
            "Rumored" -> RUMORED
            "Planned" -> PLANNED
            "In Production" -> IN_PRODUCTION
            "Post Production" -> POST_PRODUCTION
            "Released" -> RELEASED
            "Canceled" -> CANCELED
            else -> RUMORED
        }
    }
}


