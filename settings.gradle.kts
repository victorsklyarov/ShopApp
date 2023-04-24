pluginManagement {
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
rootProject.name = "ShopApp"
include(":app")
include(":core:data")
include(":core:domain")
include(":core:presentation")
include(":feature:cart")
include(":feature:chat")
include(":feature:favorite")
include(":feature:home")
include(":feature:login")
include(":feature:profile")
include(":feature:signin")
