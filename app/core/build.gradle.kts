plugins {
    id("objectiverewards.android.library")
    id("objectiverewards.android.hilt")
}

android {
    namespace = "com.andreivanceadev.objectiverewards.core"
}

dependencies {

    testImplementation(libs.test.jUnit)
    androidTestImplementation(libs.test.jUnitX)
    testImplementation(libs.test.turbine)

    compileOnly(libs.test.turbine)
}

kapt {
    correctErrorTypes = true
}
