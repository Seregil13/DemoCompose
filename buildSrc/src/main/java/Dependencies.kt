
object Dependencies {

    object Compose {
        const val version = "1.2.0-beta01"

        const val runtime = "androidx.compose.runtime:runtime:${version}"
        const val ui = "androidx.compose.ui:ui:${version}"
        const val foundationLayout = "androidx.compose.foundation:foundation-layout:${version}"
        const val material = "androidx.compose.material:material:${version}"
        const val icons = "androidx.compose.material:material-icons-extended:${version}"
        const val foundation = "androidx.compose.foundation:foundation:${version}"
        const val animation = "androidx.compose.animation:animation:${version}"
        const val uiTooling = "androidx.compose.ui:ui-tooling:${version}"
        const val livedata = "androidx.compose.runtime:runtime-livedata:${version}"
        const val navigation = "androidx.navigation:navigation-compose:2.5.1"
        const val activity = "androidx.activity:activity-compose:1.4.0"
        const val constraint = "androidx.constraintlayout:constraintlayout-compose:1.0.0"
        const val viewmodel = "androidx.lifecycle:lifecycle-viewmodel-compose:2.4.1"
    }

    object Ktor {
        const val version = "2.0.3"

        const val core = "io.ktor:ktor-client-core:$version"
        const val engine = "io.ktor:ktor-client-cio:$version"
        const val okhttp = "io.ktor:ktor-client-okhttp:$version"
        const val logging = "io.ktor:ktor-client-logging:$version"
        const val contentNegotiation = "io.ktor:ktor-client-content-negotiation:$version"
        const val serialization = "io.ktor:ktor-serialization-kotlinx-json:$version"
    }

    object AndroidX {

        object Paging {
            const val runtime =  "androidx.paging:paging-runtime-ktx:3.1.1"
            const val compose = "androidx.paging:paging-compose:1.0.0-alpha15"
        }

        object Room {
            const val version = "2.4.3"

            const val runtime = "androidx.room:room-runtime:$version"
            const val compiler = "androidx.room:room-compiler:$version"
            const val ktx = "androidx.room:room-ktx:$version"
            const val paging = "androidx.room:room-paging:$version"
        }

        const val ktx = "androidx.core:core-ktx1.7.0"
        const val appcompat = "androidx.appcompat:appcompat1.4.2"
        const val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:2.3.1"
    }

    const val googleMaterial = "com.google.android.material:material:1.6.1"
    const val timber = "com.jakewharton.timber:timber:5.0.1"

    object KotlinX {
        const val serializationJson = "org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2"
        const val datetime = "org.jetbrains.kotlinx:kotlinx-datetime:0.4.0"
    }

    object Coil {
        const val version = "2.1.0"
        const val core = "io.coil-kt:coil:${version}"
        const val compose = "io.coil-kt:coil-compose:${version}"
    }

    object Koin {
        const val version = "3.2.0"

        const val core = "io.insert-koin:koin-core:${version}"
        const val android = "io.insert-koin:koin-android:${version}"
        const val compose = "io.insert-koin:koin-androidx-compose:${version}"
    }

    object Test {
        const val junit = "junit:junit:4.13.2"
        const val androidxJunit = "androidx.test.ext:junit:1.1.3"
        const val espresso = "androidx.test.espresso:espresso-core:3.4.0"
    }
}