import org.springframework.boot.gradle.tasks.bundling.BootJar
plugins {
    kotlin("jvm")
    kotlin("plugin.jpa")
}

dependencies {
    implementation(project(mapOf("path" to ":common")))
    val queryDslVersion: String by project

    kapt("com.querydsl:querydsl-apt:$queryDslVersion")
    runtimeOnly("com.mysql:mysql-connector-j")

    implementation(project(":domain"))

    implementation("com.querydsl:querydsl-jpa:$queryDslVersion")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
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
