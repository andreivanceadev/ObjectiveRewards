package com.andreivanceadev

import org.gradle.api.JavaVersion

object ProjectConfiguration {
    const val compileSdk = 34
    const val minSdk = 26
    const val targetSdk = 34
    val compatibility = JavaVersion.VERSION_17
}