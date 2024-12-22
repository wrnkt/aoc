plugins {
    `swift-application`
    xctest
}

application {
    targetMachines.add(machines.linux.x86_64)
}
