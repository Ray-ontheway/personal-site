plugins {
	id("spring-conventions")
}

group = "top.rayc"
version = "0.0.1-SNAPSHOT"

//repositories {
//	maven { setUrl("https://maven.aliyun.com/repository/central") }
//	mavenCentral()
//}

dependencies {
	api("org.springframework.boot:spring-boot-starter-data-redis")
	api("org.springframework.boot:spring-boot-starter-security")
	api("org.springframework.boot:spring-boot-starter-mail")
	api("org.springframework.boot:spring-boot-starter-validation")
	api("org.springframework.boot:spring-boot-starter-web")
	api("org.springframework.boot:spring-boot-starter-aop")
	implementation(libs.mybatis.plus)
	api(libs.jackson.datatype.jsr310)
	api(libs.hutool)
	api(libs.commons.lang3)
	api(libs.jakarta.api)
	api(libs.jakarta.jsp)
	api(libs.jjwt.impl)
	api(libs.jjwt.jackson)
	api(libs.jjwt.api)

	api("com.fasterxml.jackson.module:jackson-module-kotlin")
	api("org.jetbrains.kotlin:kotlin-reflect")


	developmentOnly("org.springframework.boot:spring-boot-devtools")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
