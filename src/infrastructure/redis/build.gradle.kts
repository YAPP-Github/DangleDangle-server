import org.springframework.boot.gradle.tasks.bundling.BootJar
plugins {
    kotlin("jvm")
}
dependencies {
    val embeddedRedisVersion: String by project

    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("it.ozimov:embedded-redis:$embeddedRedisVersion")

    implementation(project(":auth"))
}

tasks.named<BootJar>("bootJar") {
    enabled = false
}

tasks.named<Jar>("jar") {
    enabled = true
}
