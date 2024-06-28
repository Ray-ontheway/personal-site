plugins {
    `java-library`
}

val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(libs.findVersion("jdk").get().toString()))
    }
    withSourcesJar()
    withJavadocJar()
}