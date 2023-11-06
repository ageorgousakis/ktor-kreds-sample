import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.ktor)
}

group = "com.example"
version = "0.0.1"

application {
    mainClass.set("com.example.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "${JavaVersion.VERSION_17}"
        }
    }

    withType<Test> {
        useJUnitPlatform()
    }

    java {
        toolchain {
            languageVersion = JavaLanguageVersion.of(17)
        }
    }
}

dependencies {
    implementation(libs.bundles.ktor.server)
    implementation(libs.ktor.server.netty)
//    implementation(libs.ktor.server.websockets)

    implementation(libs.bundles.ktor.koin)

    implementation(libs.bundles.hoplite)

    implementation(libs.logback.classic)
    
    implementation(libs.kreds)

    testImplementation(libs.kotlin.test.junit)
    testImplementation(libs.ktor.server.tests.jvm)
}
