// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    id("org.jetbrains.kotlin.jvm") version "2.0.0" apply false
    // Remove the attempt to alias compose.compiler if it's not in libs.versions.toml
    // The Compose compiler version can be set directly in the module-level build file
}

// Define the compose compiler version as a variable if needed across subprojects
val composeCompilerVersion = "1.6.0"
