import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.7.5"
    id("io.spring.dependency-management") version "1.1.4"
    kotlin("jvm") version "1.9.22"
    kotlin("plugin.spring") version "1.9.22"
    kotlin("plugin.jpa") version "1.9.22"
    kotlin("kapt") version "1.8.22"
    kotlin("plugin.serialization") version "1.6.21"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
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

    //jpa
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")

    //DB
    implementation("org.springframework.boot:spring-boot-starter-data-redis-reactive")
    runtimeOnly("com.mysql:mysql-connector-j")

    //security
    implementation("org.springframework.boot:spring-boot-starter-security")

    //validation
    implementation("org.springframework.boot:spring-boot-starter-validation")

    //jwt
    implementation("io.jsonwebtoken:jjwt:0.9.1")

    //jackson
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    //kotlin
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    //test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")

    // logging
    implementation("io.github.microutils:kotlin-logging-jvm:2.0.10")

    //aws
    implementation("com.amazonaws:aws-java-sdk-s3:1.12.232")

    //mail
    implementation("org.springframework.boot:spring-boot-starter-mail")

    // vault
    implementation("com.bettercloud:vault-java-driver:5.1.0")

    // kubernetes
    implementation("io.kubernetes:client-java:15.0.1")
    implementation("com.amazonaws:aws-java-sdk-eks:1.11.595")
    implementation("software.amazon.awssdk:sts:2.17.64")

    //openFeign
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign:3.1.4")

    // json
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.3")

}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
