repositories {
    mavenCentral()
}

plugins {
    `kotlin-dsl`
}

kotlinDslPluginOptions {
    experimentalWarning.set(false)
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.5.0")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.5.0")
}
