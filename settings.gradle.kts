pluginManagement {
    val springBootVersion: String by settings
    val ktlintVersion: String by settings
    val springDependencyManagementVersion: String by settings
    val jibVersion: String by settings
    val kotlinVersion: String by settings

    resolutionStrategy {
        eachPlugin {
            when (requested.id.id) {
                "org.springframework.boot" -> useVersion(springBootVersion)
                "io.spring.dependency-management" -> useVersion(springDependencyManagementVersion)
                "org.jlleitschuh.gradle.ktlint" -> useVersion(ktlintVersion)
                "org.jetbrains.kotlin.plugin.jpa" -> useVersion(kotlinVersion)
                "com.google.cloud.tools.jib" -> useVersion(jibVersion)
                "org.jetbrains.kotlin.jvm" -> useVersion(kotlinVersion)
                "org.jetbrains.kotlin.kapt" -> useVersion(kotlinVersion)
                "org.jetbrains.kotlin.plugin.spring" -> useVersion(kotlinVersion)
            }
        }
    }
}

rootProject.name = "yapp22-be"

// app
includeProject(":api", "src/app/api")

// domain
includeProject(":domain", "src/domain")

// support
includeProject(":logger", "src/support/logger")
includeProject(":common", "src/support/common")
includeProject(":lock", "src/support/lock")
includeProject(":mail", "src/support/mail")

// infrastructure
includeProject(":storage", "src/infrastructure/storage")
includeProject(":client", "src/infrastructure/client")
includeProject(":redis", "src/infrastructure/redis")

fun includeProject(name: String, projectPath: String) {
    include(name)
    project(name).projectDir = file(projectPath)
}
