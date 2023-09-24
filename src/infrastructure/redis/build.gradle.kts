import org.springframework.boot.gradle.tasks.bundling.BootJar
plugins {
    kotlin("jvm")
    `java-test-fixtures`
}
dependencies {
    implementation(project(":domain"))
    implementation(project(":common"))

    implementation("org.springframework.boot:spring-boot-starter-data-redis")
}

tasks.named<BootJar>("bootJar") {
    enabled = false
}

tasks.named<Jar>("jar") {
    enabled = true
}
