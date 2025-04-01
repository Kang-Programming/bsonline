plugins {
	id("org.springframework.boot") version "3.4.4"
	id("io.spring.dependency-management") version "1.1.7"
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	kotlin("plugin.jpa") version "1.9.25"
	kotlin("kapt") version "2.1.20"
}

group = "com.khi"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	// Spring Boot JPA
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")

	// Querydsl (Jakarta 지원 버전)
	implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")
	kapt("com.querydsl:querydsl-apt:5.0.0:jakarta")

	// 보안
	implementation("org.springframework.boot:spring-boot-starter-security")

	// Jakarta API 관련 라이브러리 (명확히 지정)
	kapt("jakarta.annotation:jakarta.annotation-api:2.1.1")
	kapt("jakarta.persistence:jakarta.persistence-api:3.1.0")

	implementation("com.fasterxml.jackson.core:jackson-databind:2.14.1")
	implementation("org.jetbrains.kotlin:kotlin-reflect:1.9.20")

	// Spring Boot Web
	implementation("org.springframework.boot:spring-boot-starter-web")

	// Hibernate (Spring Boot 3.x 호환 버전)
	implementation("org.hibernate:hibernate-core:6.6.11.Final")

	// 데이터베이스 (Oracle, 변경 필요시 다른 DB 드라이버 추가)
	runtimeOnly("com.oracle.database.jdbc:ojdbc11")

	// 개발 편의성
	developmentOnly("org.springframework.boot:spring-boot-devtools")

	// 테스트 관련
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}
val generated = file("src/main/generated")
// querydsl QClass 파일 생성 위치를 지정
tasks.withType<JavaCompile> {
	options.generatedSourceOutputDirectory.set(generated)
}

// kotlin source set 에 querydsl QClass 위치 추가
sourceSets {
	main {
		kotlin.srcDirs += generated
	}
}

// gradle clean 시에 QClass 디렉토리 삭제
tasks.named("clean") {
	doLast {
		generated.deleteRecursively()
	}
}
kapt {
	generateStubs = true
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
