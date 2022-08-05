import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

@Suppress("DSL_SCOPE_VIOLATION")
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
    implementation("io.ktor:ktor-client-core:2.0.3")
    implementation("io.ktor:ktor-client-cio:2.0.3")
    implementation("io.ktor:ktor-client-okhttp:2.0.3")
    implementation("io.ktor:ktor-client-logging:2.0.3")
    implementation("io.ktor:ktor-client-content-negotiation:2.0.3")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.0.3")
}

buildConfig {
    buildConfigField("String", "tmdbApiKey", gradleLocalProperties(rootDir).getProperty("tmdb"))

    useKotlinOutput {
        this.internalVisibility = true
    }
}
