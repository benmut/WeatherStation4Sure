plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    alias(libs.plugins.hilt.android)
    id("kotlin-kapt")
}

android {
    namespace = "com.mutondo.weatherstation4sure"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.mutondo.weatherstation4sure"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    secrets {
        propertiesFileName = "secrets.properties"
        defaultPropertiesFileName = "local.defaults.properties"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // Maps Compose
    implementation(libs.maps.compose)

    // Navigation Compose
    implementation(libs.androidx.navigation.compose)

    // Retrofit & Gson & Logging
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.logging.interceptor)

    // Dagger hilt
    implementation (libs.hilt.android)
    implementation (libs.hilt.navigation.compose)
    kapt(libs.hilt.android.compiler)

    // Preferences DataStore
    implementation (libs.androidx.datastore.preferences)

    // Google Play Service
    implementation (libs.play.services.location)

    // Coroutines
    implementation (libs.kotlinx.coroutines.core)
    implementation (libs.kotlinx.coroutines.play)

    // Coil
    implementation (libs.coil.compose)

    // ConstraintLayout Compose
    implementation(libs.androidx.constraintlayout.compose)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

kapt {
    correctErrorTypes = true
}
