import org.springframework.boot.gradle.tasks.bundling.BootJar
plugins {
    kotlin("jvm")
}
dependencies {
    val embeddedRedisVersion: String by project

    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    testImplementation("it.ozimov:embedded-redis:$embeddedRedisVersion")

    implementation(project(":auth"))
    implementation(project(":common"))
}

tasks.named<BootJar>("bootJar") {
    enabled = false
}

tasks.named<Jar>("jar") {
    enabled = true
}
