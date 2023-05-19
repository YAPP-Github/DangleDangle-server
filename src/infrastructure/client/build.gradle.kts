import org.springframework.boot.gradle.tasks.bundling.BootJar

dependencies{
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
    implementation("org.springframework.cloud:spring-cloud-starter-circuitbreaker-resilience4j")
}

tasks.named<BootJar>("bootJar") {
    enabled=false
}

tasks.named<Jar>("jar") {
    enabled = true
}