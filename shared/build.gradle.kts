import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinxSerialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kmpNativeCoroutines)
    kotlin("native.cocoapods")
}

kotlin {
    cocoapods {
        // Configuración del framework para Cocoapods
        version = "1.16.2"
        summary = "Descripción del módulo shared"
        homepage = "https://tu-proyecto.com"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../iosApp/Podfile") // Ruta al Podfile del proyecto iOS
        framework {
            baseName = "Shared"
        }
    }


    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Shared"
            isStatic = true
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
        commonMain.dependencies {
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.koin.core)
            api(libs.kmp.observable.viewmodel)
        }

        // Required by KMM-ViewModel
        all {
            languageSettings.optIn("kotlinx.cinterop.ExperimentalForeignApi")
            languageSettings.optIn("kotlin.experimental.ExperimentalObjCName")
        }
    }
}

android {
    namespace = "com.mula.kmpapp.shared"
    compileSdk = 35
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    defaultConfig {
        minSdk = 24
    }
}
tasks.register("setupSharedSourceSets") {
    doLast {
        val sharedSrc = file("src")
        listOf(
            "commonMain/kotlin",
            "commonMain/resources",
            "androidMain/kotlin",
            "iosMain/kotlin",
            "iosMain/resources"
        ).forEach {
            file("$sharedSrc/$it").mkdirs()
        }
        println("Carpetas de código compartido creadas correctamente.")
    }
    notCompatibleWithConfigurationCache("Esta tarea modifica el sistema de archivos.")
}
