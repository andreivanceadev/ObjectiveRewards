object Versions {
    const val kotlin = "1.8.21"
    const val coreKtx = "1.10.1"
    const val lifecycleKtx = "2.6.1"
    const val activityCompose = "1.7.2"
    const val composeBom = "2023.05.01"
    const val composeNav = "2.6.0"

    const val hilt = "2.44"
    const val kotlinCompilerExtVersion = "1.4.7"

    object Testing {
        const val jUnit = "4.13.2"
        const val jUnitX = "1.1.5"
        const val espressoCore = "3.5.1"
    }
}

object Libs {
    const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    const val lifecycleKtx = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycleKtx}"

    object Compose {
        const val activity = "androidx.activity:activity-compose:${Versions.activityCompose}"
        const val bom = "androidx.compose:compose-bom:${Versions.composeBom}"
        const val ui = "androidx.compose.ui:ui"
        const val graphics = "androidx.compose.ui:ui-graphics"
        const val preview = "androidx.compose.ui:ui-tooling-preview"
        const val material = "androidx.compose.material3:material3"
        const val tooling = "androidx.compose.ui:ui-tooling"
        const val manifest = "androidx.compose.ui:ui-test-manifest"
        const val navigation = "androidx.navigation:navigation-compose:${Versions.composeNav}"
    }

    object Hilt {
        const val hilt = "com.google.dagger:hilt-android:${Versions.hilt}"
        const val hiltCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"
    }

    object Testing {
        const val jUnit = "junit:junit:${Versions.Testing.jUnit}"
        const val jUnitX = "androidx.test.ext:junit:${Versions.Testing.jUnitX}"
        const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.Testing.espressoCore}"
        const val composeJunit = "androidx.compose.ui:ui-test-junit4"
    }
}