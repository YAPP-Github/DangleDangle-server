import org.springframework.boot.gradle.tasks.bundling.BootJar
plugins {
    kotlin("jvm")
    kotlin("plugin.jpa")
}

dependencies {
    val queryDslVersion: String by project
    val testContainerVersion: String by project

    kapt("com.querydsl:querydsl-apt:$queryDslVersion")
    runtimeOnly("com.mysql:mysql-connector-j")

    implementation(project(":auth"))
    implementation(project(":volunteer"))
    implementation(project(":common"))
    implementation(project(":shelter"))
    implementation(project(":volunteerEvent"))
    implementation(project(":redis"))

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("com.querydsl:querydsl-jpa:$queryDslVersion")

    testImplementation("org.testcontainers:testcontainers:$testContainerVersion")
    testImplementation("org.testcontainers:junit-jupiter:$testContainerVersion")
    testImplementation("org.testcontainers:mysql:$testContainerVersion")

    testImplementation(testFixtures(project(":shelter")))
    testImplementation(testFixtures(project(":auth")))
    testImplementation(testFixtures(project(":volunteer")))
    testImplementation(testFixtures(project(":volunteerEvent")))
}

allOpen { // 추가적으로 열어줄 allOpen
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}

tasks {
    val copySql by registering(Copy::class) {
        from("src/main/resources/sql/1.ddl.sql")
        into("src/test/resources/sql")
        includeEmptyDirs = false
    }

    named<Test>("test") {
        dependsOn(copySql)
    }

    named<BootJar>("bootJar") {
        enabled = false
    }

    named<Jar>("jar") {
        enabled = true
    }
}
