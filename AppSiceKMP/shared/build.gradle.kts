import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import androidx.room.gradle.RoomExtension

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidMultiplatformLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
    alias(libs.plugins.ktorfit)
}

kotlin {
    /*
    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Shared"
            isStatic = true
        }
    }
     */

    androidLibrary {
        namespace = "com.example.appsicekmp.shared"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()

        compilerOptions {
            jvmTarget = JvmTarget.JVM_11
        }
        androidResources {
            enable = true
        }
        /*
        withHostTest {
            isIncludeAndroidResources = true
        }
         */

        lint {
            abortOnError = false
        }
    }

    //Destktop


    sourceSets.all {
        languageSettings.optIn(
            "androidx.room.RoomDatabaseConstructor"
        )
    }

    jvm("desktop") {
        compilations.all {
            compilerOptions.configure {
                jvmTarget.set(JvmTarget.JVM_11)
            }
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.room.ktx)
        }
        commonMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.material.icons.extended)
            // Extension de iconos
            //implementation(libs.compose.material.icons.extended)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            // ROOM
            //implementation(libs.androidx.room.runtime)
            //implementation(libs.androidx.room.common)
            implementation(libs.androidx.room.runtime)
            implementation(libs.androidx.sqlite.bundled)
            //implementation(libs.androidx.room.ktx)
            // Ktorfit
            implementation(libs.ktorfit)
            // Ktor
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.okhttp)
            implementation(libs.ktor.client.contentNegotiation)
            implementation(libs.ktor.serialization.xml)
            //implementation(libs.ktor.client.logging)
            implementation(libs.ktor.serialization.json)
            implementation(libs.ktor.serialization.kotlinx.json)
            // Serializaation
            implementation(libs.kotlinx.serialization.json)
            // Navigation
            implementation(libs.androidx.navigation.compose)
            // Coil
            implementation(libs.coil.compose)


        }

        val desktopMain by getting {
            dependencies {
                //Aquí irán tus dependencias de escritorio
                implementation(libs.ktor.client.cio)
            }
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

    }
}

configure<RoomExtension> {
    schemaDirectory(
        "$projectDir/schemas"
    )
}

dependencies {
    // Room
    //add("kspCommonMainMetadata", libs.androidx.room.compiler)
    add("kspAndroid", libs.androidx.room.compiler)
    //add("kspIosSimulatorArm64", libs.androidx.room.compiler)
    //add("kspIosArm64", libs.androidx.room.compiler)
    add("kspDesktop", libs.androidx.room.compiler)
    add("kspCommonMainMetadata", libs.ktorfit.ksp)
    // Ktorfit

}

configurations.all {
    exclude(
        group = "com.google.devtools.ksp"
    )
}