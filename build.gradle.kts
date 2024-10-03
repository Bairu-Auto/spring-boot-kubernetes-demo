plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.3.4"
    id("io.spring.dependency-management") version "1.1.6"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

val allureVersion = "2.25.0"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.springframework.boot:spring-boot-starter-web")

    // -------------------- TEST scope --------------------------------------
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // Import allure-bom to ensure correct versions of all the dependencies are used
    testImplementation(platform("io.qameta.allure:allure-bom:$allureVersion"))
    testImplementation("io.qameta.allure:allure-junit5")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
        events("passed", "failed", "skipped")
        showExceptions = true
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
    }

    // Configure Allure output directory
    systemProperty("allure.results.directory", file("build/allure-results"))

    // Set Allure output directory
    outputs.dir(layout.buildDirectory.dir("allure-results"))
}

tasks.register<Exec>("allureReport") {
    group = "reporting"
    description = "Generate Allure Report"
    commandLine("allure", "generate", "build/allure-results", "-o", "build/allure-report")
}

tasks.register<Exec>("allureServe") {
    group = "reporting"
    description = "Serve Allure Report"
    commandLine("allure", "serve", "build/allure-results")
}
