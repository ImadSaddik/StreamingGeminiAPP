plugins {
    alias(libs.plugins.androidApplication)
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.streaminggeminiapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.streaminggeminiapp"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

//    Dependencies for the Gemini API library
    implementation("com.google.ai.client.generativeai:generativeai:0.1.2")
    implementation("com.google.guava:guava:31.0.1-android")
    implementation("org.reactivestreams:reactive-streams:1.0.4")

//    Dependencies for the Kotlin Coroutines library (This is what you should add)
    implementation("androidx.core:core-ktx:+")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactive:1.7.3")

}