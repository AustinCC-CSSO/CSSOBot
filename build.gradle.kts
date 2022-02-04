plugins {
    application
    kotlin("jvm") version "1.6.10"
    kotlin("plugin.serialization") version "1.6.10"
    id("com.github.johnrengelman.shadow") version "7.1.0"
}

group = "dev.twelveoclock"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {

    runtimeOnly("org.slf4j:slf4j-simple:1.7.35")

    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")

    implementation("dev.twelveoclock.lang:CrescentLang:+")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")

    implementation("net.dv8tion:JDA:5.0.0-alpha.5") {
        // Exclude audio stuff
        exclude(module = "opus-java")
    }
}

application {
    mainClass.set("dev.twelveoclock.cssobot.CSSOBot")
}

tasks {

    compileKotlin {

        sourceCompatibility = JavaVersion.VERSION_17.toString()
        targetCompatibility = JavaVersion.VERSION_17.toString()
        kotlinOptions.jvmTarget = JavaVersion.VERSION_17.toString()
        //kotlinOptions.languageVersion = "1.6"
        //kotlinOptions.apiVersion = "1.6"
        //kotlinOptions.useFir = true

        kotlinOptions.freeCompilerArgs = listOf(
            "-Xjvm-default=compatibility",
            "-Xmulti-platform",
            "-Xuse-experimental=kotlin.ExperimentalStdlibApi",
            "-Xuse-experimental=kotlin.time.ExperimentalTime",
            "-Xuse-experimental=kotlin.ExperimentalUnsignedTypes"
        )
    }

    compileJava {
        sourceCompatibility = JavaVersion.VERSION_17.toString()
        targetCompatibility = JavaVersion.VERSION_17.toString()
    }

}