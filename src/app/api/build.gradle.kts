import io.spring.gradle.dependencymanagement.org.codehaus.plexus.interpolation.os.Os
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

    compileOnly("org.springframework:spring-tx")

    runtimeOnly(project(":client"))
    runtimeOnly(project(":storage"))
    runtimeOnly(project(":redis"))
    runtimeOnly("com.h2database:h2")

    runtimeOnly("io.jsonwebtoken:jjwt-impl:$jsonWebTokenVersion")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:$jsonWebTokenVersion")

    implementation(project(":lock"))
    implementation(project(":common"))
    implementation(project(":auth"))
    implementation(project(":volunteer"))
    implementation(project(":volunteerEvent"))
    implementation(project(":shelter"))
    implementation(project(":event"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$openApiVersion")
    implementation("io.jsonwebtoken:jjwt-api:$jjwtApiVersion")

    testImplementation(testFixtures(project(":storage")))
    testImplementation(testFixtures(project(":redis")))

    if (Os.isFamily(Os.FAMILY_MAC)) {
        // for-mac
        implementation("io.netty:netty-resolver-dns-native-macos:4.1.86.Final:osx-aarch_64")
    }
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
