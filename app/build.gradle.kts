plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    // Add the Google services Gradle plugin
    id("com.google.gms.google-services")

    id("kotlin-kapt")
    id("kotlin-parcelize")

    id("com.google.dagger.hilt.android")
}

android {
    namespace = "mx.equipo6.proyectoapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "mx.equipo6.proyectoapp"
        minSdk = 24
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
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    // Core Android dependencies
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // Compose and UI dependencies
    implementation(platform(libs.androidx.compose.bom))

    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)

    // Lifecycle and ViewModel
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)

    // Dependency Injection (Hilt)
    implementation(libs.dagger.hilt.android)
    implementation(libs.androidx.ui.tooling.preview.android)
    kapt(libs.dagger.hilt.android.compiler)

    // Networking (Retrofit, Gson, Volley)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)
    implementation(libs.volley)

    // Google Play and Google Pay APIs
    implementation(libs.play.services.wallet)
    implementation("com.google.pay.button:compose-pay-button:0.1.3")

    // Stripe Payments
    implementation(libs.stripe.android)

    // Coil (Image Loading)
    implementation(libs.coil.compose)

    // Accompanist Pager (For paging in Compose)
    implementation(libs.accompanist.pager)

    // Firebase dependencies
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)

    // OpenStreetMap (OSMDroid)
    implementation(libs.osmdroid.android)

    // Material Design
    implementation("com.google.android.material:material:1.9.0")
    implementation(libs.androidx.material.icons.extended)

    // Testing dependencies
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))

    implementation ("androidx.appcompat:appcompat:1.7.0")

}
