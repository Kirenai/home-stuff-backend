plugins {
    java
    kotlin("jvm") version "2.1.21"
    kotlin("plugin.spring") version "2.1.21"
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
}

group = "me.kire.re"
version = "0.1.0-SNAPSHOT"

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

extra["springCloudVersion"] = "2025.0.0"

dependencies {
    implementation(project(":validation"))
    implementation(project(":exceptions"))
    implementation(libs.spring.boot.mongodb.reactive)
    implementation(libs.spring.boot.webflux)
    implementation(libs.spring.boot.eureka.client)
    implementation(libs.spring.boot.actuator)
    implementation(libs.micrometer.tracing.bridge.brave)
    implementation(libs.zipkin.reporter.brave)
    runtimeOnly(libs.micrometer.registry.prometheus)
    implementation(libs.mapstruct)
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)
    annotationProcessor(libs.mapstruct.processor)
    testImplementation(libs.spring.boot.test)
    testImplementation(libs.reactor.test)
    testRuntimeOnly(libs.junit.launcher)
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}