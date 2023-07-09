import org.springframework.boot.gradle.tasks.bundling.BootJar
plugins {
    kotlin("jvm")
}

dependencies {
    val redissonVersion: String by project

    implementation(project(":common"))
    implementation("org.springframework.boot:spring-boot-starter-aop")
    implementation("org.redisson:redisson-spring-boot-starter:$redissonVersion")
}

tasks.named<BootJar>("bootJar") {
    enabled = false
}

tasks.named<Jar>("jar") {
    enabled = true
}
