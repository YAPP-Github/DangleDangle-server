import org.springframework.boot.gradle.tasks.bundling.BootJar
plugins {
    kotlin("jvm")
    `java-test-fixtures`
}

dependencies {
    compileOnly("org.springframework:spring-tx")
    compileOnly("org.springframework:spring-context")
    compileOnly(project(":common"))
}

tasks.named<BootJar>("bootJar") {
    enabled = false
}

tasks.named<Jar>("jar") {
    enabled = true
}
