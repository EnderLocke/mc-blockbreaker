import org.gradle.api.file.DuplicatesStrategy

plugins {
    id("maven-publish")
    id("fabric-loom") version "1.10-SNAPSHOT"
    id ("org.jetbrains.kotlin.jvm") version "2.1.0"

}

sourceSets {
    main {
        resources {
            srcDirs("src/main/resources")
        }
    }
}

group = "com.ender.blockbreaker"
version = "0.0.1"

repositories {
    mavenCentral()
    maven { url = uri("https://maven.fabricmc.net/") }
    maven { url = uri("https://maven.cafeteria.dev/releases") }
    maven { url = uri("https://jitpack.io") }
    maven { url = uri("https://maven.neoforged.net/releases") }
    maven { url = uri("https://maven.impactdev.net/repository/development/") }


}

dependencies {
    minecraft("com.mojang:minecraft:${project.property("minecraft_version")}")
    mappings("net.fabricmc:yarn:${project.property("yarn_mappings")}:v2")
    modImplementation("net.fabricmc:fabric-loader:${project.property("loader_version")}")
    modImplementation("net.fabricmc.fabric-api:fabric-api:${project.property("fabric_version")}")
    modImplementation("com.cobblemon:fabric:${project.property("cobblemon_version")}")
    modImplementation("net.fabricmc:fabric-language-kotlin:${project.property("fabric_kotlin_version")}")
    // Kotlin
    implementation(kotlin("stdlib"))
}

tasks {
    jar {
        from("LICENSE") {
            rename { "${project.name}_LICENSE" }
        }
    }

    processResources {
        inputs.property("version", project.version)

        filesMatching("fabric.mod.json") {
            expand("version" to project.version)
        }
    }
}

tasks.processResources {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

