package com.example.network

import DemoCompose.network.BuildConfig
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

object KtorClient {
    private const val AUTHORIZATION_HEADER = "Authorization"
    private const val apiKey = BuildConfig.tmdbApiKey

    private const val BASE_URL = "https://api.themoviedb.org/3/"

    var logger: NetworkLogger? = null

    private val json = Json {
        prettyPrint = true
        isLenient = true
        ignoreUnknownKeys = true
    }

    val client = HttpClient(CIO) {
        defaultRequest {
            url(BASE_URL)
            header(AUTHORIZATION_HEADER, "Bearer $apiKey")
            accept(ContentType.Application.Json)
        }

        install(ContentNegotiation) {
            json(json)
        }

        install(Logging) {
            level = LogLevel.ALL
            logger = object : Logger {
                override fun log(message: String) {
                    this@KtorClient.logger?.log(message)
                }
            }
        }
    }
}

