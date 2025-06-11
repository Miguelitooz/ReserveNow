plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.tfgAndroid"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.tfgAndroid"
        minSdk = 24
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    dependencies { // Este bloque se mueve aquí dentro
        implementation(libs.appcompat)
        implementation(libs.material)
        implementation(libs.activity)
        implementation ("androidx.cardview:cardview:1.0.0")
        implementation(libs.constraintlayout)
        testImplementation(libs.junit)
        androidTestImplementation(libs.ext.junit)
        androidTestImplementation(libs.espresso.core)
        implementation("com.android.volley:volley:1.2.1") // Agrega esta línea para Volley
        implementation ("com.squareup.retrofit2:retrofit:2.9.0")
        implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    }
}