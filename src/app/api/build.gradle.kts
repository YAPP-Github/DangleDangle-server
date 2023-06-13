import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    kotlin("jvm")
    id("com.google.cloud.tools.jib")
    jacoco
}

dependencies {
    val openApiVersion: String by project

    runtimeOnly(project(":client"))
    runtimeOnly(project(":storage"))
    runtimeOnly(project(":common"))

    compileOnly("org.springframework:spring-tx")

    implementation(project(":auth"))
    implementation(project(":user"))
    implementation(project(":volunteerEvent"))
    implementation(project(":shelter"))
    compileOnly(project(":common"))
    compileOnly(project(":storage"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$openApiVersion")
    implementation("org.springframework.security:spring-security-oauth2-client")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("io.jsonwebtoken:jjwt-api:0.11.2")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.2")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.2")
}

jacoco {
    toolVersion = "0.8.8"
}

tasks.test {
    finalizedBy(tasks.jacocoTestReport)
}
tasks.jacocoTestReport {
    dependsOn(tasks.test)
    finalizedBy(tasks.jacocoTestCoverageVerification)
}

tasks.jacocoTestCoverageVerification {
    violationRules {
        rule {
            limit {
                minimum = BigDecimal(0.20)
            }
        }
    }
}
tasks.named<BootJar>("bootJar") {
    archiveFileName.set("api.jar")
}

tasks.named<Jar>("jar") {
    enabled = false
}
