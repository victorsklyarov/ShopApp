@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.zeroillusion.shopapp"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.zeroillusion.shopapp"
        minSdk = 21
        targetSdk = 33
        versionCode = 2
        versionName = "1.1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
    packaging {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
}

dependencies {
    implementation(project(":core:data"))
    implementation(project(":core:domain"))
    implementation(project(":feature:cart"))
    implementation(project(":feature:chat"))
    implementation(project(":feature:favorite"))
    implementation(project(":feature:home"))
    implementation(project(":feature:login"))
    implementation(project(":feature:profile"))
    implementation(project(":feature:signin"))

    implementation(libs.bundles.common)
    implementation(libs.bundles.retrofit)
    kapt(libs.room.compiler)
    implementation(libs.bundles.room)
    kapt(libs.hilt.android.compiler)
    implementation(libs.bundles.hilt)
    implementation(libs.androidx.navigation.compose)
}

kapt {
    correctErrorTypes = true
}