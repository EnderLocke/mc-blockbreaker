rootProject.name = "blockbreaker"

pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven { url = uri("https://maven.fabricmc.net/") }
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        maven { url = uri("https://maven.fabricmc.net/") }
        maven { url = uri("https://maven.impactdev.net/repository/development/") }
        maven { url = uri("https://maven.neoforged.net/releases") }
    }
}