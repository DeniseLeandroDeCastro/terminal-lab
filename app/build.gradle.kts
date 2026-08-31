plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "br.com.denisecastro.cielopaylab"

    compileSdk {
        version = release(37) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        applicationId = "br.com.denisecastro.cielopaylab"

        minSdk = 26
        targetSdk = 36

        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner =
            "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    // =========================================================
    // ANDROID CORE
    // =========================================================
    implementation(libs.androidx.core.ktx)

    // =========================================================
    // LIFECYCLE / VIEWMODEL
    // =========================================================
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // =========================================================
    // JETPACK COMPOSE
    // =========================================================
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)

    // =========================================================
    // NAVIGATION
    // =========================================================
    implementation(libs.androidx.navigation.compose)

    // =========================================================
    // COROUTINES
    // =========================================================
    implementation(libs.kotlinx.coroutines.android)

    // =========================================================
    // ROOM
    // =========================================================
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    // =========================================================
    // HILT
    // =========================================================
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    // =========================================================
    // RETROFIT
    // =========================================================
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.moshi)

    // =========================================================
    // MOSHI
    // =========================================================
    implementation(libs.moshi)
    implementation(libs.moshi.kotlin)

    // =========================================================
    // TESTES UNITÁRIOS
    // =========================================================
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockwebserver)

    // =========================================================
    // TESTES INSTRUMENTADOS / COMPOSE
    // =========================================================
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)

    // =========================================================
    // DEBUG
    // =========================================================
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)
}