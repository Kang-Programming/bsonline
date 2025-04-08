import org.springframework.boot.gradle.tasks.bundling.BootJar

val querydslVersion: String = "5.0.0"

plugins {
    id("org.jetbrains.kotlin.kapt") version "2.1.20"
    kotlin("plugin.jpa")
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation(project(":security"))
    implementation(project(":common"))
    implementation(project(":user"))
    implementation(project(":order"))
    implementation(project(":product"))
    implementation(project(":log"))
    implementation(project(":statistics"))

    val kapt by configurations
    api("org.springframework.boot:spring-boot-starter-data-jpa")
    api("com.querydsl:querydsl-jpa:$querydslVersion:jakarta") // ":jakarta" classifier 추가
    kapt("com.querydsl:querydsl-apt:$querydslVersion:jakarta") // ":jakarta" classifier 추가
    kapt("org.springframework.boot:spring-boot-configuration-processor")
    implementation("com.github.gavlyukovskiy:p6spy-spring-boot-starter:1.8.0")
    implementation("org.springframework.boot:spring-boot-starter-security")

    implementation("org.springframework.boot:spring-boot-starter-web")

    // jakarta.persistence API만 명시적으로 사용
    implementation("jakarta.persistence:jakarta.persistence-api:3.1.0")

    // Servlet API 추가
    implementation("jakarta.servlet:jakarta.servlet-api:6.0.0") // 또는 사용 중인 Spring Boot 버전에 맞는 버전

    runtimeOnly("com.oracle.database.jdbc:ojdbc11")
}

tasks.bootJar {
    mainClass.set("com.khi.bs.BsApplicationKt")
    enabled = true
}

tasks.jar {
    enabled = false
}
tasks.bootRun {
    mainClass.set("com.khi.bs.BsApplicationKt")
    enabled = true
}

springBoot {
    mainClass.set("com.khi.bs.BsApplicationKt")
}