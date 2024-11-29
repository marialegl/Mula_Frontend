pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        }
    }

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Mula_Fronted"

include(":composeApp")
include(":shared")
include(":iosApp")