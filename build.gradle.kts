plugins {
    id("org.springframework.boot") version ("2.7.5")
    id("io.spring.dependency-management") version ("1.1.0")
    id("java")
    id ("org.sonarqube") version ("4.3.0.3225")
}

group = "org.seclore"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation ("org.slf4j:slf4j-api:2.0.9")
    implementation ("org.springframework.boot:spring-boot-starter-logging")
    implementation ("org.springframework.boot:spring-boot-starter")
    implementation ("org.springframework.boot:spring-boot-starter-validation")
    implementation ("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation ("org.springframework.boot:spring-boot-starter-web")
    implementation ("net.sourceforge.tess4j:tess4j:4.5.3")
    compileOnly ("org.projectlombok:lombok:1.18.20")
    annotationProcessor ("org.projectlombok:lombok:1.18.20")
    implementation("com.google.cloud:google-cloud-vision:3.53.0")
    runtimeOnly ("com.h2database:h2")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

sonar {
    properties {
        property ("sonar.projectKey", "dummy")
        property ("sonar.qualitygate.wait", true)
    }
}
