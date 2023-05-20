import org.springframework.boot.gradle.tasks.bundling.BootJar

dependencies {
    compileOnly("org.springframework:spring-tx")
    compileOnly("org.springframework:spring-context")
}

tasks.named<BootJar>("bootJar") {
    enabled = false
}

tasks.named<Jar>("jar") {
    enabled = true
}
