import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "com.andreivanceadev.objectiverewards.buildlogic"

// Configure the build-logic plugins to target JDK 17
// This matches the JDK used to build the project, and is not related to what is running on device.
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ktLint.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("application") {
            id = "objectiverewards.android.application"
            implementationClass = "com.andreivanceadev.AndroidApplicationConventionPlugin"
        }
        register("library.compose") {
            id = "objectiverewards.android.library.compose"
            implementationClass = "com.andreivanceadev.AndroidLibraryComposeConventionPlugin"
        }
        register("application.compose") {
            id = "objectiverewards.android.application.compose"
            implementationClass = "com.andreivanceadev.AndroidApplicationComposeConventionPlugin"
        }
        register("hilt") {
            id = "objectiverewards.android.hilt"
            implementationClass = "com.andreivanceadev.AndroidHiltConventionPlugin"
        }
        register("library") {
            id = "objectiverewards.android.library"
            implementationClass = "com.andreivanceadev.AndroidLibraryConventionPlugin"
        }
        register("feature") {
            id = "objectiverewards.android.feature"
            implementationClass = "com.andreivanceadev.AndroidFeatureConventionPlugin"
        }
        register("room") {
            id = "objectiverewards.android.room"
            implementationClass = "com.andreivanceadev.AndroidRoomConventionPlugin"
        }
        register("ktlint") {
            id = "objectiverewards.android.ktlint"
            implementationClass = "com.andreivanceadev.AndroidKtLintConventionPlugin"
        }
    }
}