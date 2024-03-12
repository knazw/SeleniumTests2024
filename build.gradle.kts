plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // https://mvnrepository.com/artifact/org.junit/junit-bom
    testImplementation(platform("org.junit:junit-bom:5.10.1"))

    // https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.1")

    // https://mvnrepository.com/artifact/io.github.bonigarcia/webdrivermanager
    testImplementation("io.github.bonigarcia:webdrivermanager:5.7.0")
    // https://mvnrepository.com/artifact/org.seleniumhq.selenium/selenium-java
    testImplementation("org.seleniumhq.selenium:selenium-java:4.18.1")
    // https://mvnrepository.com/artifact/org.assertj/assertj-core
    testImplementation("org.assertj:assertj-core:3.24.2")
    // https://mvnrepository.com/artifact/org.slf4j/slf4j-api
    testImplementation("org.slf4j:slf4j-api:2.0.9")
    // https://mvnrepository.com/artifact/io.rest-assured/rest-assured
    testImplementation("io.rest-assured:rest-assured:5.4.0")
    // https://mvnrepository.com/artifact/ch.qos.logback/logback-classic
    testImplementation("ch.qos.logback:logback-classic:1.5.3")
    // https://mvnrepository.com/artifact/com.fasterxml.jackson.datatype/jackson-datatype-jsr310
    testImplementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.16.0")
// https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter-params
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.10.1")


}

tasks.test {
    useJUnitPlatform()
}