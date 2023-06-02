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

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$openApiVersion")
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
