rootProject.name = "lisp_interpreter"

include(
    "model",
    "command_line_runner",
)

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}


/**
 * [libraries]
 * spring-boot-starter = { module = "org.springframework.boot:spring-boot-starter" }
 * spring-boot-starter-test = { module = "org.springframework.boot:spring-boot-starter-test" }
 * kotlin-reflect = { module = "org.jetbrains.kotlin:kotlin-reflect" }
 *
 * [plugins]
 * kotlin = { id = "org.jetbrains.kotlin.jvm", version.ref = "kotlin" }
 * kotlin-spring = { id = "org.jetbrains.kotlin.plugin.spring", version.ref = "kotlin" }
 * spring-boot = { id = "org.springframework.boot", version = "3.0.6" }
 * spring-dependency-management = { id = "io.spring.dependency-management", version = "1.1.0" }
 */