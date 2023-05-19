import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    kotlin("jvm")
    id("com.google.cloud.tools.jib")
}

dependencies {
    val openApiVersion: String by project

    runtimeOnly(project(":client"))
    runtimeOnly(project(":storage"))

    implementation(project(":auth"))
    implementation(project(":animal"))
    implementation(project(":volunteer"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$openApiVersion")
}

tasks.named<BootJar>("bootJar") {
    archiveFileName.set("api.jar")
}

tasks.named<Jar>("jar") {
    enabled = false
}
