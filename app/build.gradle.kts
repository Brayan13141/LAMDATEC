plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.devtools.ksp)
    id("com.google.gms.google-services")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.lamdatec"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.lamdatec"
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
        viewBinding = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.13"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildToolsVersion = "35.0.0"
}

dependencies {
    //FIREBASE
    implementation(platform("com.google.firebase:firebase-bom:33.4.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-database-ktx:20.3.2")

    //WORK
    implementation("androidx.work:work-runtime-ktx:2.9.1")


    //IMPLEMENTACION PARA LAS FUENTES Y LOS SERVICIOS
    implementation(libs.vision.internal.vkp)
    implementation(libs.androidx.ui.text.google.fonts)
    implementation("com.google.android.gms:play-services-base:18.2.0")
    implementation(libs.firebase.database.ktx)
    implementation(libs.firebase.database.ktx)


    //IMPLEMENTACION PARA GRAFICOS
    //implementation("com.github.tehras:charts:0.2.4-alpha")
    implementation("co.yml:ycharts:2.1.0")




    // Jetpack Compose Integration
    implementation("androidx.compose.ui:ui:1.5.0")         // Jetpack Compose UI
    implementation("androidx.compose.material:material:1.5.0")// Material para Compose
    implementation("androidx.compose.ui:ui-tooling:1.5.0")
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.play.services.vision)
    implementation(libs.core)
    implementation(libs.androidx.foundation.layout.android)

    // Navigation
    implementation(libs.navigation.compose)
    // Mas iconos
    implementation(libs.androidx.material.icons.extended.android)
    // inyeccion de dependencias
    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)
    ksp(libs.hilt.android.compiler)
    // corrutinas
    implementation(libs.kotlinx.coroutines)
    // workmanager
    implementation(libs.androidx.work.runtime.ktx)
    // viewmodel
    implementation(libs.androidx.lifecycle.viewmodel)



    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}