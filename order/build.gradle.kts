plugins {
    id("org.jetbrains.kotlin.kapt") version "2.1.20"
    kotlin("plugin.jpa")
}
val querydslVersion: String = "5.0.0"
repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation(project(":common"))
    implementation(project(":user"))
    implementation(project(":product"))

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("com.querydsl:querydsl-jpa:$querydslVersion:jakarta")
    kapt("com.querydsl:querydsl-apt:$querydslVersion:jakarta")
    kapt("org.springframework.boot:spring-boot-configuration-processor")

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    runtimeOnly("com.oracle.database.jdbc:ojdbc11")
}

tasks.bootJar {
    enabled = false
}

tasks.jar {
    enabled = true
}

tasks.test {
    enabled = false
}

tasks.bootRun {
    enabled = false
}