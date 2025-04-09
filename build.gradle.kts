import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar
import org.springframework.boot.gradle.tasks.run.BootRun

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

	tasks.withType<KotlinCompile>().configureEach { // configureEach 사용 권장 (Gradle 8.x+)
		compilerOptions { // 'kotlinOptions' 대신 'compilerOptions' 사용
			jvmTarget.set(JvmTarget.JVM_17) // 문자열 "17" 대신 JvmTarget enum 사용 및 .set() 메소드 사용
			// 다른 컴파일러 옵션이 필요하다면 여기에 추가
			// 예: freeCompilerArgs.add("-Xjsr305=strict")
			// 예: apiVersion.set(org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_2_1) // 사용하는 Kotlin 버전에 맞춰 설정 가능
			// 예: languageVersion.set(org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_2_1)
		}
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

tasks.withType<BootRun> {
	group = "application" // 태스크 그룹 지정 (선택 사항)
	description = "Runs the core application module." // 태스크 설명 (선택 사항)
	dependsOn(":core:bootRun") // 루트의 bootRun이 :core:bootRun에 의존하도록 설정
}