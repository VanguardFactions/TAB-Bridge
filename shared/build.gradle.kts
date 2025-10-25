plugins {
    `java-library`
    id("io.freefair.lombok")
}

dependencies {
    compileOnly("org.jetbrains:annotations:24.0.0")
    compileOnly("net.luckperms:api:5.4")
    compileOnly("io.freefair.gradle:lombok-plugin:6.6.1")
    compileOnly("com.google.guava:guava:31.1-jre")
    // VANGUARD ESSENTIALS HOOK
    compileOnly("com.vanguardfactions:essentials-plugin:0.0.1")
    compileOnly("com.vanguardfactions:core-api:0.0.1")
}