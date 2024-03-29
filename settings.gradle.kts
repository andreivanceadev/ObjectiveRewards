pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "ObjectiveRewards"
include(":app")
include(":app:features:dashboard")
include(":app:features:designsystem")
include(":app:data")
include(":app:features:objectives")
include(":app:core")
include(":app:features:rewards")
