import org.springframework.boot.gradle.tasks.bundling.BootJar
plugins {
    kotlin("jvm")
    `java-test-fixtures`
}

dependencies {
    compileOnly("org.springframework:spring-tx")
    compileOnly("org.springframework:spring-context")
    implementation("org.springframework.security:spring-security-oauth2-client")
    implementation("org.springframework.boot:spring-boot-starter-security")
    compileOnly(project(":common"))
    compileOnly(project(":redis"))
}
tasks.named<BootJar>("bootJar") {
    enabled = false
}

tasks.named<Jar>("jar") {
    enabled = true
}
