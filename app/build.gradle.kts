plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    compileSdk = 32

    defaultConfig {
        applicationId = "com.example.democompose"
        minSdk = 26
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.2.0"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":network"))
    implementation(project(":domain"))

    implementation("androidx.paging:paging-runtime-ktx:3.1.1")
    implementation("androidx.paging:paging-compose:1.0.0-alpha15")
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")

    implementation("io.coil-kt:coil:2.1.0")
    implementation("io.coil-kt:coil-compose:2.1.0")
    implementation("com.jakewharton.timber:timber:5.0.1")

    implementation("io.insert-koin:koin-core:3.2.0")
    implementation("io.insert-koin:koin-android:3.2.0")
    implementation("io.insert-koin:koin-androidx-compose:3.2.0")

    implementation("androidx.compose.runtime:runtime:1.2.0-beta01")
    implementation("androidx.compose.ui:ui:1.2.0-beta01")
    implementation("androidx.compose.foundation:foundation-layout:1.2.0-beta01")
    implementation("androidx.compose.material:material:1.2.0-beta01")
    implementation("androidx.compose.material:material-icons-extended:1.2.0-beta01")
    implementation("androidx.compose.foundation:foundation:1.2.0-beta01")
    implementation("androidx.compose.animation:animation:1.2.0-beta01")
    implementation("androidx.compose.ui:ui-tooling:1.2.0-beta01")
    implementation("androidx.compose.runtime:runtime-livedata:1.2.0-beta01")
    implementation("androidx.navigation:navigation-compose:2.5.1")
    implementation("androidx.activity:activity-compose:1.4.0")
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.4.1")

    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}