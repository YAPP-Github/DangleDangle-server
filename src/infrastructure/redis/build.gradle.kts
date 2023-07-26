import org.springframework.boot.gradle.tasks.bundling.BootJar
plugins {
    kotlin("jvm")
    `java-test-fixtures`
}
dependencies {
    val testContainerVersion: String by project
    val kotestVersion: String by project

    implementation(project(":auth"))
    implementation(project(":event"))
    implementation(project(":common"))

    implementation("org.springframework.boot:spring-boot-starter-data-redis")

    testFixturesImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testFixturesImplementation("org.testcontainers:testcontainers:$testContainerVersion")
    testFixturesImplementation("org.testcontainers:junit-jupiter:$testContainerVersion")
}

tasks.named<BootJar>("bootJar") {
    enabled = false
}

tasks.named<Jar>("jar") {
    enabled = true
}
