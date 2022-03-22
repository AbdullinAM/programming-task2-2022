import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.31"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    application
}

group = "me.vladdenisov"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.github.ajalt.clikt:clikt:3.4.0")
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.6.0")
}

tasks.test {
    useJUnit()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}
//tasks.withType<KotlinTest>() {
//
//}
//tasks.jar {
//    manifest {
//        attributes(
//            "Main-Class" to application.mainClass
//        )
//    }
//    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
//    from(sourceSets.main.get().output)
//    dependsOn(configurations.runtimeClasspath)
//    from({
//        configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
//    })
//}

tasks.shadowJar {
    archiveBaseName.set("ls")
    archiveVersion.set(version)
    minimize()
}