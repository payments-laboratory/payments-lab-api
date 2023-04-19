dependencies {
	implementation(project(":account-api:account-domain"))
	implementation(project(":common"))

	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
}

tasks.withType<org.springframework.boot.gradle.tasks.bundling.BootJar> {
	enabled = false
}

