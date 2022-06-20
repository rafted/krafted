import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.0"
    application

    id("org.jlleitschuh.gradle.ktlint") version "10.3.0"
}

group = "io.github.kraftedmc"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.netty:netty-all:4.1.78.Final")
    implementation("it.unimi.dsi:fastutil:8.5.8")
    implementation("ch.qos.logback:logback-core:1.2.11")
    implementation("org.slf4j:slf4j-api:1.7.5")
    implementation("org.slf4j:slf4j-simple:1.6.4")
    implementation("commons-codec:commons-codec:1.15")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.5.21")
    compileOnly("ch.qos.logback:logback-classic:1.2.11")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}

application {
    mainClass.set("MainKt")
}
