@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.zeroillusion.shopapp.feature.home"
    compileSdk = 33

    defaultConfig {
        minSdk = 21
        targetSdk = 33

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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.androidxComposeUi.get()
    }
}

dependencies {
    implementation(project(":core:domain"))
    implementation(project(":core:presentation"))

    implementation(libs.bundles.common)
    implementation(libs.bundles.retrofit)
    implementation(libs.coil.kt.compose)
    kapt(libs.room.compiler)
    implementation(libs.bundles.room)
    kapt(libs.hilt.android.compiler)
    implementation(libs.bundles.hilt)
}

kapt {
    correctErrorTypes = true
}