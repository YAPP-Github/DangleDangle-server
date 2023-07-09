import org.springframework.boot.gradle.tasks.bundling.BootJar
plugins {
    kotlin("jvm")
}
dependencies {
    implementation(project(":auth"))
    implementation(project(":common"))

    implementation("org.springframework.boot:spring-boot-starter-data-redis")
}

tasks.named<BootJar>("bootJar") {
    enabled = false
}

tasks.named<Jar>("jar") {
    enabled = true
}
