import org.springframework.boot.gradle.tasks.bundling.BootJar
plugins {
    kotlin("jvm")
    kotlin("plugin.jpa")
}

dependencies {
    compileOnly(project(":auth"))
    compileOnly(project(":user"))
    compileOnly(project(":common"))
    compileOnly(project(":shelter"))
    compileOnly(project(":volunteerEvent"))
    compileOnly(project(":observationAnimal"))

    runtimeOnly("com.mysql:mysql-connector-j")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
}

allOpen { // 추가적으로 열어줄 allOpen
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}

tasks.named<BootJar>("bootJar") {
    enabled = false
}

tasks.named<Jar>("jar") {
    enabled = true
}
