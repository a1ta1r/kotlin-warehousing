import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.0"
}

group = "distributed_systems"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
    maven { url = uri("http://kotlin.bintray.com/ktor") }
}

dependencies {
    compile(kotlin("stdlib-jdk8"))
    compile("org.jetbrains.exposed:exposed:0.11.2")
    compile("mysql:mysql-connector-java:5.1.46")
    compile("io.ktor:ktor-server-netty:1.0.0-beta-3")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}