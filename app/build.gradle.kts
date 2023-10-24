plugins {
    id("objectiverewards.android.application")
    id("objectiverewards.android.application.compose")
    id("objectiverewards.android.hilt")
}

val appId = "com.andreivanceadev.objectiverewards"
val versionCodeNumber = 1
val versionCodeString = "0.0.1" // X.Y.Z; X = Major, Y = minor, Z = Patch level

android {
    namespace = appId

    defaultConfig {
        applicationId = appId
        versionCode = versionCodeNumber
        versionName = versionCodeString

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
        }
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(project("features:designsystem"))
    implementation(project("features:dashboard"))
    implementation(project("features:objectives"))
    implementation(project("features:rewards"))

    implementation(libs.compose.navigation)

    implementation(libs.timber)

    testImplementation(libs.test.jUnit)
    androidTestImplementation(libs.test.jUnitX)
    androidTestImplementation(libs.test.espressoCore)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.test.compose)

    debugImplementation(libs.compose.tooling)
    debugImplementation(libs.compose.manifest)
}

kapt {
    correctErrorTypes = true
}
