@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
}

android {
    namespace = "com.salesmanagement.core_network"
    compileSdk = libs.versions.compile.sdk.get().toInt()

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        minSdk = libs.versions.min.sdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        buildTypes {
            getByName("debug") {
                buildConfigField("String", "HOST", "\"https://financial-bbce1e34cd1d.herokuapp.com\"")
            }
            getByName("release") {
                buildConfigField("String", "HOST", "\"https://financial-bbce1e34cd1d.herokuapp.com\"")
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = libs.versions.java.version.get()
    }
}

dependencies {
    implementation(libs.bundles.koin)
    implementation(libs.bundles.networking)
}