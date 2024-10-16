plugins {
	kotlin("jvm") version "1.9.10"  // 최신 버전으로 교체하세요.
	id("org.springframework.boot") version "3.2.0"  // Spring Boot 버전도 최신으로 교체하세요.
	id("io.spring.dependency-management") version "1.1.3"
}

group = "com.ploy"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	implementation("org.postgresql:postgresql:42.6.0")

	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-data-redis")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.springframework.boot:spring-boot-starter")
	testImplementation("org.springframework.boot:spring-boot-starter-test")

	// Spring Security
	implementation("org.springframework.boot:spring-boot-starter-security")

	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")

	implementation("jakarta.validation:jakarta.validation-api:3.0.0")
	implementation("com.auth0:java-jwt:4.2.1")

	testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.3")
	testImplementation("org.junit.jupiter:junit-jupiter-engine:5.9.3")
	testImplementation("org.mockito:mockito-core:5.4.0")
	testImplementation("org.mockito:mockito-junit-jupiter:5.4.0")
	testImplementation("org.mockito.kotlin:mockito-kotlin:5.0.0")
	testImplementation("org.mockito:mockito-core:5.5.0")

	implementation("io.swagger.core.v3:swagger-annotations:2.2.2")
	implementation("io.swagger.core.v3:swagger-integration:2.2.2")

}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
