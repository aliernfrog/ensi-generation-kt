import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `java-library`
    `maven-publish`
    alias(libs.plugins.kotlin.jvm)
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_11)
    }
}

afterEvaluate {
    publishing {
        publications {
            register<MavenPublication>("java") {
                from(components["java"])

                groupId = "io.github.aliernfrog"
                artifactId = "ensi-generation"
                version = libs.versions.version.get()
            }
        }
    }
}