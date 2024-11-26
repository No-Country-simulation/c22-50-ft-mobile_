plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
}

android {
    namespace = "com.example.fintech_nocountry"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.fintech_nocountry"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation("com.squareup.retrofit2:retrofit:2.9.0") // Retrofit
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0") // Moshi para Retrofit
    implementation("com.squareup.moshi:moshi:1.15.0") // Moshi
    implementation("com.squareup.moshi:moshi-kotlin:1.15.0") // Moshi para Kotlin
    implementation("com.squareup.moshi:moshi-adapters:1.15.0") // Para PolymorphicJsonAdapterFactory

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.0") // Corrutinas
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.0") // Corrutinas para Android


}