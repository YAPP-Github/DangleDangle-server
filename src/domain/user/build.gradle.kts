import org.springframework.boot.gradle.tasks.bundling.BootJar
plugins {
    kotlin("jvm")
}
dependencies {
    compileOnly("org.springframework:spring-tx")
    compileOnly("org.springframework:spring-context")
    compileOnly(project(":common"))
    compileOnly(project(":storage"))
}

tasks.named<BootJar>("bootJar") {
    enabled = false
}

tasks.named<Jar>("jar") {
    enabled = true
}
