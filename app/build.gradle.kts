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

    implementation(Dependencies.AndroidX.Paging.runtime)
    implementation(Dependencies.AndroidX.Paging.compose)

    implementation(Dependencies.KotlinX.datetime)

    implementation(Dependencies.Coil.core)
    implementation(Dependencies.Coil.compose)
    implementation(Dependencies.timber)

    implementation(Dependencies.Koin.core)
    implementation(Dependencies.Koin.android)
    implementation(Dependencies.Koin.compose)

    implementation(Dependencies.Compose.runtime)
    implementation(Dependencies.Compose.ui)
    implementation(Dependencies.Compose.foundationLayout)
    implementation(Dependencies.Compose.material)
    implementation(Dependencies.Compose.icons)
    implementation(Dependencies.Compose.foundation)
    implementation(Dependencies.Compose.animation)
    implementation(Dependencies.Compose.uiTooling)
    implementation(Dependencies.Compose.livedata)
    implementation(Dependencies.Compose.navigation)
    implementation(Dependencies.Compose.activity)
    implementation(Dependencies.Compose.constraint)
    implementation(Dependencies.Compose.viewmodel)

    implementation(Dependencies.AndroidX.lifecycleRuntime)
    testImplementation(Dependencies.Test.junit)
    androidTestImplementation(Dependencies.Test.androidxJunit)
    androidTestImplementation(Dependencies.Test.espresso)
}