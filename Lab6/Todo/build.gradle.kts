buildscript {
    val agp_version by extra("8.7.1")
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.7.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.24" apply false
    kotlin("kapt") version "1.9.10" apply false
}