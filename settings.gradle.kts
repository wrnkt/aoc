/*
plugins {
    // Apply the foojay-resolver plugin to allow automatic download of JDKs
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
*/

rootProject.name = "aoc"
include("app", "java", "scala", "kotlin", "rust", "cpp", "swift")

project(":java").projectDir = file("./java")
project(":scala").projectDir = file("./scala")
project(":kotlin").projectDir = file("./kotlin")
project(":rust").projectDir = file("./rust")
project(":cpp").projectDir = file("./cpp")
project(":swift").projectDir = file("./swift")
