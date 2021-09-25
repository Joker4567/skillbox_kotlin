plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("kotlin-android-extensions")
}

android {
    signingConfigs {
        create("release") {
            storeFile = file("../mobiKey.jks")
            storePassword = "asDf4i4n"
            keyAlias = "key0"
            keyPassword = "asDf4i4n"
        }
    }
    compileSdkVersion(rootProject.extra["compileSdkVersion"] as Int)
    defaultConfig {
        applicationId = "pro.enaza.mobigames"
        minSdkVersion(rootProject.extra["minSdkVersion"] as Int)
        targetSdkVersion(rootProject.extra["compileSdkVersion"] as Int)
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        getByName("release") {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = true
            isShrinkResources = true
            isUseProguard = false
            proguardFiles("proguard-android.txt", "proguard-rules.pro")
        }
        getByName("debug") {
            isDebuggable = true
        }
    }
    lintOptions {
        isIgnoreTestSources = true
    }
    val API_ENDPOINT = "API_ENDPOINT"
    val DEV_SERVER_DOMAIN = "mobigames.test.enazadev.ru/api/v3/"
    val DEV_REST_SERVER = "\"https://$DEV_SERVER_DOMAIN\""

    val PROD_SERVER_DOMAIN = "api.mobi-games.kz/v3/"
    val PROD_REST_SERVER = "\"https://$PROD_SERVER_DOMAIN\""

    flavorDimensions("default")
    productFlavors {
        create("production") {
            dimension("default")
            buildConfigField("String",API_ENDPOINT,PROD_REST_SERVER)
        }
        create("develop") {
            dimension("default")
            buildConfigField("String",API_ENDPOINT,DEV_REST_SERVER)
        }
    }
    kapt {
        generateStubs = true
        correctErrorTypes = false
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildToolsVersion = "30.0.3"
    androidExtensions {
        isExperimental = true
    }
}

dependencies {
    implementation(project(":core-date"))
    implementation(project(":core-utils"))
    implementation(project(":core-ui"))

    implementation(project(":feature-cards"))
    implementation(project(":feature-cards-api"))

    val kotlin_version = rootProject.extra["kotlin_version"]
    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version")
    implementation("androidx.core:core-ktx:1.3.2")
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("com.google.android.material:material:1.3.0")
    //Retrofit
    val retrofit = rootProject.extra["retrofit_version"]
    implementation("com.squareup.retrofit2:retrofit:$retrofit")
    implementation("com.squareup.retrofit2:converter-moshi:$retrofit")
    implementation("com.squareup.retrofit2:converter-gson:$retrofit")
    //Stetho
    implementation("com.facebook.stetho:stetho-okhttp3:1.5.1")
    //Okhttp
    val okhttp = rootProject.extra["okHttp_version"]
    implementation("com.squareup.okhttp3:okhttp:$okhttp")
    implementation("com.squareup.okhttp3:logging-interceptor:$okhttp")

    //Dagger-Hilt
    val dagger = rootProject.extra["dagger_version"]
    val daggerViewModel = rootProject.extra["dagger_viewmodel_version"]
    val daggerWork = rootProject.extra["hilt_work_version"]
    implementation("com.google.dagger:hilt-android:$dagger")
    implementation("androidx.hilt:hilt-lifecycle-viewmodel:$daggerViewModel")
    kapt("com.google.dagger:hilt-android-compiler:$dagger")
    kapt("androidx.hilt:hilt-compiler:$daggerViewModel")
    implementation("androidx.hilt:hilt-work:$daggerWork")
    kapt("androidx.hilt:hilt-compiler:$daggerWork")
    //Room
    val room = rootProject.extra["room_version"]
    implementation("androidx.room:room-runtime:$room")
    implementation("androidx.room:room-ktx:$room")
    kapt("androidx.room:room-compiler:$room")

    implementation("org.litepal.android:core:1.4.1")

    //Android Notification DSL (In Development)
    implementation("com.kirich1409.android-notification-dsl:core:0.1.0")
    implementation("com.kirich1409.android-notification-dsl:extensions:0.1.0")
    //Проверка кода
    //https://github.com/SalomonBrys/ANR-WatchDog
    implementation("com.github.anrwatchdog:anrwatchdog:1.4.0")
    //https://square.github.io/leakcanary/getting_started/ - регистрируем утечку памяти
    //Logcat:LeakCanary
    //debugImplementation("com.squareup.leakcanary:leakcanary-android:2.7")
    //Firebase
    implementation("com.google.firebase:firebase-analytics-ktx:19.0.0")
}
