import org.springframework.boot.gradle.tasks.bundling.BootJar
plugins {
    kotlin("jvm")
}

dependencies {

}

tasks.named<BootJar>("bootJar") {
    enabled = false
}

tasks.named<Jar>("jar") {
    enabled = true
}
