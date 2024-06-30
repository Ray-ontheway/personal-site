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
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	compileOnly("org.projectlombok:lombok")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	implementation(project(":rayc-infrastructure-utility"))
	implementation(project(":rayc-user"))
}

tasks.withType<Test> {
	useJUnitPlatform()
}
