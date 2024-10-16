plugins {
    id("spring-conventions")
    kotlin("kapt")
}

group = "top.rayc"
version = "0.0.1-SNAPSHOT"

dependencies {
    implementation(libs.mapstruct)
    kapt(libs.mapstruct.proccessor)
    implementation(libs.druid.starter)
    implementation(libs.mysql)
    implementation(libs.mybatis.plus)
    implementation("org.springframework.boot:spring-boot-starter-security")
    annotationProcessor(libs.mapstruct)
    implementation(libs.jakarta.api)
//    implementation(libs.jakarta.jsp)
    implementation(project(":rayc-infrastructure-utility"))
    implementation(project(":rayc-user"))
}

tasks.withType<Jar> {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

kapt {
    arguments {
        arg("mapstruct.defaultComponentModel", "spring")
        arg("mapstruct.unmappedTargetPolicy", "IGNORE")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}