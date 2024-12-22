tasks.register("test") {
    exec {
        commandLine("cargo", "test")
    }
}

tasks.register("build") {
    exec {
        commandLine("cargo", "build")
    }
}

tasks.register("run") {
    exec {
        commandLine("cargo", "run")
    }
}
