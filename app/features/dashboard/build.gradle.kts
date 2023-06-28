plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
    id("kotlin-parcelize")
}

android {
    namespace = ConfigurationData.applicationId + ".dashboard"
    compileSdk = ConfigurationData.compileSdk

    defaultConfig {
        minSdk = ConfigurationData.minSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.kotlinCompilerExtVersion
    }

    buildFeatures {
        compose = true
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {


    implementation(project(":app:features:designsystem"))
    implementation(project(":app:data"))
    implementation(project(":app:core"))

    implementation(Libs.coreKtx)
    implementation(Libs.lifecycleKtx)
    implementation(Libs.Compose.activity)
    implementation(platform(Libs.Compose.bom))
    implementation(Libs.Compose.ui)
    implementation(Libs.Compose.graphics)
    implementation(Libs.Compose.tooling)
    implementation(Libs.Compose.material)
    implementation(Libs.Compose.coil)
    implementation(Libs.Compose.viewModelLifecycle)

    implementation(Libs.timber)

    //hilt
    implementation(Libs.Hilt.hilt)
    implementation(Libs.Hilt.hiltNavigation)
    kapt(Libs.Hilt.hiltCompiler)

    testImplementation(Libs.Testing.jUnit)
    androidTestImplementation(Libs.Testing.jUnitX)
    androidTestImplementation(Libs.Testing.espressoCore)
    androidTestImplementation(platform(Libs.Compose.bom))
    androidTestImplementation(Libs.Testing.composeJunit)

    debugImplementation(Libs.Compose.tooling)
    debugImplementation(Libs.Compose.manifest)
}


kapt {
    correctErrorTypes = true
}