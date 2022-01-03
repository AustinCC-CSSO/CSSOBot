plugins {
    application
    kotlin("jvm") version "1.6.10"
    id("com.github.johnrengelman.shadow") version "7.1.0"
}

group = "dev.twelveoclock"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {

    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")

    implementation("dev.twelveoclock.lang:CrescentLang:+")

    implementation("net.dv8tion:JDA:5.0.0-alpha.3") {
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