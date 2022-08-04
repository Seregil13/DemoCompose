import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.kotlin.plugin.serialization") version("1.7.0")
    id("com.github.gmazzo.buildconfig") version("3.1.0")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    implementation(Dependencies.Ktor.core)
    implementation(Dependencies.Ktor.engine)
    implementation(Dependencies.Ktor.okhttp)
    implementation(Dependencies.Ktor.logging)
    implementation(Dependencies.Ktor.contentNegotiation)
    implementation(Dependencies.Ktor.serialization)
}

buildConfig {
    buildConfigField("String", "tmdbApiKey", gradleLocalProperties(rootDir).getProperty("tmdb"))

    useKotlinOutput {
        this.internalVisibility = true
    }
}
