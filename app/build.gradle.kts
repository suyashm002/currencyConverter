plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.suyash.currencyconverter"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.suyash.currencyconverter"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    buildFeatures {
        compose = true
    }
    kapt {
        correctErrorTypes = true
    }
    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.13.1")
    implementation ("androidx.activity:activity-compose:1.9.2")
    implementation  ("androidx.compose:compose-bom:2024.09.02")
    implementation ("androidx.compose.ui:ui")
    implementation ("androidx.compose.ui:ui-graphics")
    implementation ("androidx.compose.ui:ui-tooling-preview")
    implementation ("androidx.compose.material3:material3:1.3.0")
    implementation ("com.google.android.material:material:1.12.0")
    implementation ("io.coil-kt:coil-compose:2.4.0")
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.6")
    implementation ("com.google.dagger:hilt-android:2.51.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter:5.8.1")
    kapt("com.google.dagger:hilt-compiler:2.51.1")
    implementation ("androidx.browser:browser:1.8.0")
    implementation ("androidx.hilt:hilt-navigation-compose:1.2.0")
    implementation ("androidx.navigation:navigation-compose:2.8.1")
    implementation ("androidx.lifecycle:lifecycle-runtime-compose:2.8.6")
    implementation ("androidx.room:room-runtime:2.6.1")
    implementation ("androidx.room:room-ktx:2.6.1")
    annotationProcessor("androidx.room:room-compiler:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")


    androidTestImplementation ("androidx.test.ext:junit:1.2.1")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.6.1")
    androidTestImplementation ("androidx.compose:compose-bom:2024.09.02")

    debugImplementation ("androidx.compose.ui:ui-tooling")
    debugImplementation ("androidx.compose.ui:ui-test-manifest")

    // For Mockito
    testImplementation("org.mockito:mockito-core:5.2.0")
// Mockito Kotlin for Kotlin extensions
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.2.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.1")
    testImplementation("androidx.arch.core:core-testing:2.1.0")
    testImplementation("app.cash.turbine:turbine:0.12.1")
}