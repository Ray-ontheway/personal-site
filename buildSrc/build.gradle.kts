// import org.jetbrains.kotlin.gradle.dsl.JvmTarget
val libs: VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
    mavenCentral()
}

dependencies {
    // buildSrc in combination with this plugin ensures that the version set here
    // will be set to the same for all other Kotlin dependencies / plugins in the project.
    // println("kotlin-gradle: ${libs.findLibrary("kotlin-gradle").get()}")
    add("implementation", libs.findLibrary("kotlin-gradle").get())
    // implementation(libs.findLibrary("kotlin-gradle").get()) // 添加依赖

    // https://kotlinlang.org/docs/all-open-plugin.html
    // contains also https://kotlinlang.org/docs/all-open-plugin.html#spring-support
    // The all-open compiler plugin adapts Kotlin to the requirements of those frameworks and makes classes annotated
    // with a specific annotation and their members open without the explicit open keyword.
    add("implementation", libs.findLibrary("kotlin-allopen").get())

    // https://kotlinlang.org/docs/no-arg-plugin.html
    // contains also https://kotlinlang.org/docs/no-arg-plugin.html#jpa-support
    // The no-arg compiler plugin generates an additional zero-argument constructor for classes
    // with a specific annotation.
    add("implementation", libs.findLibrary("kotlin-noarg").get())

    // https://docs.spring.io/spring-boot/docs/current/gradle-plugin/reference/htmlsingle/
    // The Spring Boot Gradle Plugin provides Spring Boot support in Gradle.
    // It allows you to package executable jar or war archives, run Spring Boot applications,
    // and use the dependency management provided by spring-boot-dependencies
    add("implementation", libs.findLibrary("spring-boot-gradle").get())
}