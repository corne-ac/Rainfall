// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.2.0-beta06" apply false
    id("org.jetbrains.kotlin.android") version "1.9.10" apply false
    id("com.google.dagger.hilt.android") version "2.48" apply false
    id("com.google.devtools.ksp") version "1.9.10-1.0.13"
    //Secrets gradle plugin
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin") version  "2.0.1" apply false
    id("org.jetbrains.dokka") version "1.9.0"
}