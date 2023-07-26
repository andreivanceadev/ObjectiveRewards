plugins {
    id("objectiverewards.android.library")
    id("objectiverewards.android.library.compose")
}

android {
    namespace = "com.andreivanceadev.objectiverewards.designsystem"
}

dependencies {
    implementation(libs.compose.coil)
}
