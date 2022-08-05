buildscript {
    repositories {
        gradlePluginPortal()
        google()
    }

    dependencies {
        classpath(libs.classpath.agp)
        classpath(libs.classpath.kgp)
        classpath(libs.classpath.dokka)
        classpath(libs.classpath.gms)
    }
}

allprojects {

    gradle.projectsEvaluated {
        tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().all {
            kotlinOptions {
                jvmTarget = JavaVersion.VERSION_11.toString()
                allWarningsAsErrors = true

                freeCompilerArgs = freeCompilerArgs + listOf("-opt-in=kotlin.RequiresOptIn")
            }
        }
    }
}