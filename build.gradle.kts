import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.0"
}

group = "distributed_systems"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    compile(kotlin("stdlib-jdk8"))
    compile("org.jetbrains.exposed:exposed:0.11.2")
    compile("mysql:mysql-connector-java:5.1.46")
    compile("org.xerial:sqlite-jdbc:3.21.0.1")
    compile("org.apache.poi:poi-ooxml:3.17")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}