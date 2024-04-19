plugins {
    id("java")
    id("io.qameta.allure") version "2.11.2"
}


apply(plugin = "io.qameta.allure")

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val allureVersion = "2.26.0"
val aspectJVersion = "1.9.22"

val agent: Configuration by configurations.creating {
    isCanBeConsumed = true
    isCanBeResolved = true
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.1"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.1")
    testImplementation("io.github.bonigarcia:webdrivermanager:5.7.0")
    testImplementation("org.seleniumhq.selenium:selenium-java:4.18.1")
    testImplementation("org.assertj:assertj-core:3.24.2")
    testImplementation("org.slf4j:slf4j-api:2.0.9")
    testImplementation("io.rest-assured:rest-assured:5.4.0")
    testImplementation("ch.qos.logback:logback-classic:1.5.3")
    testImplementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.16.0")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.10.1")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.2")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.10.2")
    testImplementation("org.junit.platform:junit-platform-suite:1.10.2")


    testImplementation("io.cucumber:cucumber-junit-platform-engine:7.16.1")
    testImplementation("io.cucumber:cucumber-java:7.16.1")
    testImplementation("org.junit.platform:junit-platform-suite:1.10.2")
    testImplementation("org.apache.commons:commons-collections4:4.4")
    testImplementation(platform("io.qameta.allure:allure-bom:$allureVersion"))
    testImplementation("io.qameta.allure:allure-cucumber7-jvm")
    testImplementation("io.qameta.allure:allure-junit-platform")
    testImplementation("io.qameta.allure:allure-junit5")

    testImplementation("io.cucumber:cucumber-picocontainer:7.16.1")

}

tasks.test {
    useJUnitPlatform()
//    jvmArgs = listOf(
//            "-javaagent:${agent.singleFile}"
//    )
}