plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp") version("1.7.0-1.0.6")
    id("org.jetbrains.kotlin.plugin.serialization") version("1.7.0")
}

android {
    namespace = "com.example.domain"
    compileSdk = 32

    defaultConfig {
        minSdk = 26
        targetSdk = 32

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(project(":network"))
    implementation(project(":database"))

    implementation(Dependencies.AndroidX.Paging.runtime)
    implementation(Dependencies.AndroidX.Paging.compose)

    implementation(Dependencies.AndroidX.Room.runtime)
    ksp(Dependencies.AndroidX.Room.compiler)
    implementation(Dependencies.AndroidX.Room.ktx)
    implementation(Dependencies.AndroidX.Room.paging)

    implementation(Dependencies.KotlinX.serializationJson)
    implementation(Dependencies.KotlinX.datetime)
    implementation(Dependencies.timber)

    implementation(Dependencies.Koin.core)

    implementation(Dependencies.AndroidX.ktx)
    implementation(Dependencies.AndroidX.appcompat)
    implementation(Dependencies.googleMaterial)
    testImplementation(Dependencies.Test.junit)
    androidTestImplementation(Dependencies.Test.androidxJunit)
    androidTestImplementation(Dependencies.Test.espresso)
}