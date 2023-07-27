plugins {
    id("objectiverewards.android.library")
    id("objectiverewards.android.room")
    id("objectiverewards.android.hilt")
}

android {
    namespace = "com.andreivanceadev.objectiverewards.data"
}

dependencies {

    testImplementation(libs.test.jUnit)
    androidTestImplementation(libs.test.jUnitX)
}

kapt {
    correctErrorTypes = true
}
