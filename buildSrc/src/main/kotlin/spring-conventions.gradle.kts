plugins {
    id("kotlin-conventions")
    kotlin("plugin.spring")
    // id("io.spring.dependency-management")
    id("org.springframework.boot")
}

logger.lifecycle("Enabling Kotlin Spring plugin in module ${project.path}")
apply(plugin = "org.jetbrains.kotlin.plugin.spring")

logger.lifecycle("Enabling Spring Boot plugin in module ${project.path}")
apply(plugin = "org.springframework.boot")

logger.lifecycle("Enabling Spring Boot Dependency Management in module ${project.path}")
apply(plugin = "io.spring.dependency-management")

springBoot {
    buildInfo()
}

