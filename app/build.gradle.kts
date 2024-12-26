plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.android.libraries.mapsplatform.secrets.gradle.plugin)

    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.varunkumar.expensetracker"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.varunkumar.expensetracker"
        minSdk = 30
        targetSdk = 34
        multiDexEnabled = true
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
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.generativeai)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // system ui tools
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.30.1")

    // compose runtime lifecycle
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.7")

    // biometrics
    implementation("androidx.biometric:biometric:1.2.0-alpha05")

    // extended icons
    implementation("androidx.compose.material:material-icons-extended:1.7.5")

    // dagger hilt
    implementation("com.google.dagger:hilt-android:2.51.1")
    kapt("com.google.dagger:hilt-android-compiler:2.51.1")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")

    // YCharts
    implementation("co.yml:ycharts:2.1.0")

    // Android datastore
    implementation("androidx.datastore:datastore-preferences:1.1.1")

    // Custom calendars
    val calendar_version = "2.6.1"
    implementation("com.kizitonwose.calendar:view:$calendar_version")
    implementation("com.kizitonwose.calendar:compose:$calendar_version")

    implementation("com.himanshoe:kalendar:1.3.2")

    // room database
    val room_version = "2.6.1"
    kapt("androidx.room:room-compiler:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-ktx:$room_version")

    // ml kit for transactions extraction
    implementation("com.google.mlkit:entity-extraction:16.0.0-beta5")

    // gson
    implementation("com.google.code.gson:gson:2.10.1")
}

kapt {
    correctErrorTypes = true
}