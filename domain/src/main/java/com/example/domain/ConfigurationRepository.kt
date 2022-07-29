package com.example.domain

//class ConfigurationRepository(
//    private val service: ITheMovieDbApiService,
//    private val configurationStorage: ConfigurationStorage
//) {
//    private var configuration: Configuration? = null
//
//    companion object {
//        const val CONFIG_KEY = "configuration"
//    }
//
//    interface ConfigurationStorage {
//        fun store(configuration: Configuration)
//        fun get(): Configuration?
//    }
//
//}
//
//data class Configuration(
//    val imageUrl: String,
//    val imageSizes: List<String>
//)

object ImageUrlBuilder {

    private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/"
    private const val POSTER_SIZE = "w500"

    fun getMoviePosterImageUrl(path: String) = "$IMAGE_BASE_URL$POSTER_SIZE$path"

}