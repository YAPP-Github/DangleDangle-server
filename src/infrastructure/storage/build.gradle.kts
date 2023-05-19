import org.springframework.boot.gradle.tasks.bundling.BootJar

dependencies{
    runtimeOnly("com.mysql:mysql-connector-j")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
}

tasks.named<BootJar>("bootJar") {
    enabled=false
}

tasks.named<Jar>("jar") {
    enabled = true
}