// Top-level build file where you can add configuration options common to all sub-projects/modules.

//buildscript {
//
//    dependencies {
//        classpath("com.google.dagger:hilt-android-gradle-plugin:2.51")
//    }
//}

plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.daggerHiltPlugin) apply false
    alias(libs.plugins.devToolsKsp) apply false
}

