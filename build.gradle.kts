plugins {
    id("java")
    `maven-publish`
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "ru.serj.learn"
version = "1.1"

repositories {
    mavenLocal()
    maven {
        url = uri("/home/serg/.m2/repository")
    }
    mavenCentral()
}

dependencies {
    implementation("ru.serj.learn:learn-dependency-inversion-core:1.4")
}

tasks {
    publishToMavenLocal {
        dependsOn(clean)
        dependsOn(build)
        dependsOn(shadowJar)
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = rootProject.group.toString()
            artifactId = project.name
            version = rootProject.version.toString()
            from(components["java"])
        }
    }
}


