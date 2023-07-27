package com.andreivanceadev

import com.andreivanceadev.objectiverewards.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.jlleitschuh.gradle.ktlint.KtlintExtension
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

class AndroidKtLintConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("org.jlleitschuh.gradle.ktlint")

            extensions.configure(KtlintExtension::class.java) {
                debug.set(true)
                verbose.set(true)
                android.set(true)
                outputToConsole.set(true)
                outputColorName.set("RED")
                ignoreFailures.set(false)
                reporters {
                    reporter(ReporterType.PLAIN)
                    reporter(ReporterType.CHECKSTYLE)
                }
                filter {
                    exclude("**/generated/**")
                    include("**/kotlin/**")
                    include("**/java/**")
                }
            }

            dependencies {
                "ktlintRuleset"(libs.findLibrary("lint.rules").get())
            }

        }
    }
}