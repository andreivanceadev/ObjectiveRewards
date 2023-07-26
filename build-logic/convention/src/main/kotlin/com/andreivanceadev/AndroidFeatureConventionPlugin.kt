package com.andreivanceadev


import com.andreivanceadev.objectiverewards.libs
import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("objectiverewards.android.library")
                apply("objectiverewards.android.library.compose")
                apply("kotlin-parcelize")
                apply("objectiverewards.android.hilt")
            }
            extensions.configure<LibraryExtension> {
                defaultConfig {
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }
                composeOptions {
                    kotlinCompilerExtensionVersion = libs.findVersion("kotlinCompilerExt").get().toString()
                }
            }

            dependencies {
                add("implementation", project(":app:features:designsystem"))
                add("implementation", project(":app:data"))
                add("implementation", project(":app:core"))

                add("implementation", libs.findLibrary("core.ktx").get())
                add("implementation", libs.findLibrary("lifecycle.ktx").get())
                add("implementation", libs.findLibrary("compose.activity").get())
                add("implementation", libs.findLibrary("compose.coil").get())
                add("implementation", libs.findLibrary("compose.viewModel").get())

                add("testImplementation", libs.findLibrary("test.jUnit").get())
                add("androidTestImplementation", libs.findLibrary("test.jUnitX").get())
                add("androidTestImplementation", libs.findLibrary("test.espressoCore").get())
                add("androidTestImplementation", platform(libs.findLibrary("compose.bom").get()))
                add("androidTestImplementation", libs.findLibrary("test.compose").get())

                add("debugImplementation", libs.findLibrary("compose.tooling").get())
                add("debugImplementation", libs.findLibrary("compose.manifest").get())
            }
        }
    }
}