plugins {
    id 'java'
}

group = 'com.dreamteam'
version = '1.0-SNAPSHOT'

sourceCompatibility = 11
targetCompatibility = 11

repositories {
    mavenCentral()
}

dependencies {
    testImplementation 'org.junit.jupiter:junit-jupiter-api:5.9.2'
    testRuntimeOnly 'org.junit.jupiter:junit-jupiter-engine:5.9.2'
    testImplementation 'org.junit.jupiter:junit-jupiter-params:5.9.2'
}

test {
    useJUnitPlatform()
    testLogging {
        events "passed", "skipped", "failed"
    }
}
