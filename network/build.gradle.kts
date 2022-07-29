import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
    alias(libs.plugins.serialization)
    alias(libs.plugins.buildConfig)
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    implementation(libs.bundles.ktor)
}

buildConfig {
    buildConfigField("String", "tmdbApiKey", gradleLocalProperties(rootDir).getProperty("tmdb"))

    useKotlinOutput {
        this.internalVisibility = true
    }
}
