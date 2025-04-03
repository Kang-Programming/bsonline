import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
	kotlin("jvm") version "2.1.20" apply false
	id("org.jetbrains.kotlin.kapt") version "2.1.20" apply false
	id("org.springframework.boot") version "3.4.4" apply false
	id("io.spring.dependency-management") version "1.1.7" apply false
	kotlin("plugin.jpa") version "2.1.20" apply false
}

allprojects {
	apply {
		plugin("kotlin")
		plugin("org.jetbrains.kotlin.kapt")
		plugin("org.springframework.boot")
		plugin("io.spring.dependency-management")
		plugin("org.jetbrains.kotlin.plugin.jpa")
	}

	group = "com.khi.bs"
	version = "0.0.1-SNAPSHOT"

	repositories {
		mavenCentral()
		gradlePluginPortal()
	}

	dependencies {
		val implementation by configurations
		val testImplementation by configurations

		implementation("org.jetbrains.kotlin:kotlin-reflect")
		implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

		testImplementation("com.ninja-squad:springmockk:3.1.0")
		testImplementation("org.springframework.boot:spring-boot-starter-test") {
			exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
			exclude(module = "mockito-core")
		}
	}

	configure<JavaPluginExtension> {
		sourceCompatibility = JavaVersion.VERSION_17
		targetCompatibility = JavaVersion.VERSION_17
	}

	tasks.withType<KotlinCompile> {
	}

	tasks.withType<Test> {
		useJUnitPlatform()
	}
}

subprojects {
	repositories {
		mavenCentral()
	}
	configure<org.gradle.api.tasks.SourceSetContainer> {
		named("main") {
			resources.srcDirs(rootProject.file("src/main/resources"))
		}
	}
}

tasks.withType<BootJar> {
//	mainClass.set("com.khi.bs.BsApplicationKt")
	enabled = false
}

tasks.withType<Jar>{
	enabled = false
}

tasks.withType<Test>{
	enabled = false
}