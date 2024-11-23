plugins {
    java
    //scala
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.slf4j.api)
    implementation(libs.logback.classic)

    implementation(libs.guava)
    testImplementation(libs.junit.jupiter)

    implementation(libs.scala.library)

    testRuntimeOnly(libs.junit.platform.launcher)
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

application {
    mainClass = "wrnkt.aoc.Main"
}

/*
sourceSets {
    main {
        scala {
            srcDir("src/main/scala")  // Scala source directory
        }
        java {
            srcDir("src/main/java")   // Java source directory (if you mix Java and Scala)
        }
    }
    test {
        scala {
            srcDir("src/test/scala")  // Scala test directory
        }
        java {
            srcDir("src/test/java")   // Java test directory (if you have Java tests)
        }
    }
}
*/


tasks.named<Test>("test") {
    useJUnitPlatform()
}
