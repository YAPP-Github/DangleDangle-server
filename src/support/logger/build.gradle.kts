import org.springframework.boot.gradle.tasks.bundling.BootJar

dependencies{
    val logbackVersion: String by project
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
}

tasks.named<BootJar>("bootJar") {
    enabled=false
}

tasks.named<Jar>("jar") {
    enabled = true
}