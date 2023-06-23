import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    kotlin("jvm")
    id("com.google.cloud.tools.jib")
    jacoco
}

dependencies {
    val openApiVersion: String by project
    val jsonWebTokenVersion: String by project
    val jjwtApiVersion: String by project

    runtimeOnly(project(":client"))
    runtimeOnly(project(":storage"))
    runtimeOnly("com.h2database:h2")

    runtimeOnly("io.jsonwebtoken:jjwt-impl:$jsonWebTokenVersion")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:$jsonWebTokenVersion")

    compileOnly("org.springframework:spring-tx")

    implementation(project(":common"))
    implementation(project(":auth"))
    implementation(project(":volunteer"))
    implementation(project(":volunteerEvent"))
    implementation(project(":shelter"))
    implementation(project(":redis"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$openApiVersion")
    implementation("io.jsonwebtoken:jjwt-api:$jjwtApiVersion")
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
                minimum = BigDecimal(0.0)
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
