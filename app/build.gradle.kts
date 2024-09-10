plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.google.gms.google.services)
    alias(libs.plugins.kotlin.kapt)
}

android {
    namespace = "com.example.aqufishnewui20"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.aqufishnewui20"
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

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.storage.ktx)
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.androidx.room.common)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.ui.text.google.fonts)
    //implementation(libs.androidx.media3.exoplayer)
    implementation(libs.androidx.media3.common)
    //implementation(libs.androidx.ui.desktop)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.2")
    implementation(platform("com.google.firebase:firebase-bom:33.1.0"))
    implementation("com.google.firebase:firebase-auth")
    implementation ("com.google.firebase:firebase-auth-ktx:23.0.0")
    implementation ("com.google.firebase:firebase-firestore-ktx:25.0.0")
    implementation("com.google.firebase:firebase-analytics")

    implementation("androidx.compose.animation:animation:1.6.8")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")

    implementation ("androidx.compose.runtime:runtime-livedata:1.3.2")
    implementation ("androidx.constraintlayout:constraintlayout-compose:1.0.1")
    implementation ("androidx.compose.ui:ui-viewbinding:1.3.2")
    implementation ("androidx.webkit:webkit:1.6.0")

    // WorkManager
    implementation("androidx.work:work-runtime-ktx:2.8.1")

    // Room
    //apply plugin: 'kotlin-kapt'
    implementation("androidx.room:room-runtime:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")

    // Other libraries
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")

    //implementation("androidx.media3:media3-exoplayer:1.4.0")
    //implementation("androidx.media3:media3-exoplayer-rtsp:1.4.0")
    implementation (libs.exoplayer.v2181)

    implementation("com.google.android.exoplayer:exoplayer-core:2.19.1")
    //implementation("com.google.android.exoplayer:exoplayer:2.19.1")
    //implementation ("com.google.android.exoplayer:extension-rtsp:2.18.1")
    //implementation ("com.google.android.exoplayer:extension-core:2.18.1")
    implementation("com.google.android.exoplayer:exoplayer-rtsp:2.19.1")
    //implementation("androidx.media3:media3-exoplayer-rtsp:1.4.0-rc-01")
    //implementation("androidx.media3:media3-ui:1.4.0")
    implementation ("com.google.android.exoplayer:exoplayer-ui:2.19.1")

    implementation("androidx.compose.foundation:foundation")

    //implementation(libs.androidx.media3.exoplayer.core)


    implementation(libs.navigation.compose)
    implementation(libs.kotlinx.serialization.json)

    implementation ("com.squareup.okhttp3:okhttp:4.12.0")

    implementation(libs.androidx.core.splashscreen)
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")


    //implementation ("tv.danmaku.ijk.media:ijkplayer:0.8.8")
    //implementation ("com.mcxiaoke.ijk.media:ijkplayer-java:0.6.2")
    //implementation ("com.github.dynckathline:ijkplayer-exo2:1.0")

}