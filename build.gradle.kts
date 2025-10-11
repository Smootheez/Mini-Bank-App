plugins {
    java
    id("org.springframework.boot") version "3.5.6"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "dev.smootheez"
version = "0.0.1-SNAPSHOT"
description = "A mini bank backend system using Java, PostgreSQL, Docker, Rest API"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
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

val jwtVersion = "0.13.0"

dependencies {
    // === Implementation Dependencies ===
    implementation("io.jsonwebtoken:jjwt-api:${jwtVersion}")
    implementation("ch.qos.logback:logback-classic")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.flywaydb:flyway-core")
    implementation("org.flywaydb:flyway-database-postgresql")

    // === Runtime-Only Dependencies ===
    runtimeOnly("io.jsonwebtoken:jjwt-impl:${jwtVersion}")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:${jwtVersion}")
    runtimeOnly("org.postgresql:postgresql")

    // === Compile-Only Dependencies ===
    compileOnly("org.projectlombok:lombok")

    // === Annotation Processors ===
    annotationProcessor("org.projectlombok:lombok")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    // === Test Dependencies ===
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}


tasks.withType<Test> {
    useJUnitPlatform()
}
