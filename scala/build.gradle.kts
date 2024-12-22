plugins {
    scala
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.scala.library)
    implementation(libs.guava)

    testImplementation(libs.junit)
    testImplementation(libs.scalatest.v2.v13)
    testImplementation(libs.junit.v4.v13.v2.v13)
    testRuntimeOnly(libs.scala.xml.v2.v13)
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

application {
    mainClass = "aoc.Main"
}
