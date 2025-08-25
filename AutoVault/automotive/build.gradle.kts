plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.autovault"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.autovault"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.13"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    useLibrary ("android.car")

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.9.0")

    // Jetpack Compose
    implementation("androidx.compose.ui:ui:1.6.7")
    implementation("androidx.compose.material3:material3:1.2.1")
    implementation("androidx.compose.ui:ui-tooling-preview:1.6.7")
    implementation("androidx.navigation:navigation-compose:2.7.7")
    implementation(libs.androidx.room.common.jvm)
    implementation(libs.androidx.room.runtime.android)
    implementation(libs.androidx.lifecycle.viewmodel.android)
    implementation(libs.androidx.appcompat) // ✅ FIXED

    debugImplementation("androidx.compose.ui:ui-tooling:1.6.7")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.6.7")

    // Firebase BOM (✅ Required fix)
    implementation(platform("com.google.firebase:firebase-bom:32.7.3"))
    implementation("com.google.firebase:firebase-firestore-ktx")

    // Location Services
    implementation("com.google.android.gms:play-services-location:21.1.0")

    // Jetpack WorkManager
    implementation("androidx.work:work-runtime-ktx:2.9.0")

    // AndroidX Security (Encrypted file store)
    implementation("androidx.security:security-crypto:1.1.0-alpha06")

//    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))


        implementation("com.google.code.gson:gson:2.10.1")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")


    implementation("com.google.android.gms:play-services-location:21.0.1")





}
