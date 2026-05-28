plugins {
    java
    `java-library`
    id("io.qameta.allure") version "4.0.2"
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21

    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

val allureVersion = "2.34.0"
val restAssuredVersion = "6.0.0"
val junitVersion = "6.1.0"
val jacksonVersion = "2.21.3"
val assertjVersion = "3.27.7"
val aspectjVersion = "1.9.25"
val selenideVersion = "7.18.0"
val cucumberVersion = "7.34.3"

dependencies {
    // JUnit 5
    testImplementation("org.junit.jupiter:junit-jupiter:${junitVersion}")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    // Rest Assured для API-тестов
    implementation("io.rest-assured:rest-assured:${restAssuredVersion}")
    // Allure для отчетов
    testImplementation("io.qameta.allure:allure-junit5:${allureVersion}")
    testImplementation("io.qameta.allure:allure-cucumber7-jvm:${allureVersion}")
    // Jackson для JSON
    implementation("com.fasterxml.jackson.core:jackson-databind:${jacksonVersion}")
    // AssertJ для ассертов
    implementation("org.assertj:assertj-core:${assertjVersion}")
    // Selenide
    implementation("com.codeborne:selenide:${selenideVersion}")
    // Cucumber
    implementation("io.cucumber:cucumber-java:${cucumberVersion}")
    testImplementation("io.cucumber:cucumber-junit-platform-engine:${cucumberVersion}")
}

tasks.test {
    useJUnitPlatform()

    systemProperty("base.url", System.getProperty("base.url", "http://localhost:8080"))

    testLogging {
        events = setOf(
            org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED,
            org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED,
            org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED
        )
        showStandardStreams = true
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
    }
}

allure {
    version.set(allureVersion)
}

tasks.register("testAndReport") {
    dependsOn(tasks.test)
    finalizedBy(tasks.allureServe)
}