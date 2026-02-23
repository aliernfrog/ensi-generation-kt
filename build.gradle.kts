plugins {
    alias(libs.plugins.kotlin.jvm) apply false
}
buildscript {
    dependencies {
        classpath(libs.kgp)
    }
}