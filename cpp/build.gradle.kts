plugins {
    `cpp-application`
    `cpp-unit-test`
}

application {
    targetMachines.add(machines.linux.x86_64)
}

tasks.register("run") {
    dependsOn("runCpp")
}

tasks.register("runCpp") {
    exec {
        commandLine("sh", "./build/install/main/debug/cpp")
    }
}
