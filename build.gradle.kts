import java.util.*

plugins {
    java
    `java-library`
    `maven-publish`
    checkstyle
    jacoco
    id("com.github.hierynomus.license") version "0.15.0"
    id("org.checkerframework") version "0.5.19"
}

group = "st.proximy.memoize"
version = "0.1.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.+")
    testImplementation("org.assertj:assertj-core:3.+")
}

tasks {
    val jacocoTestReport by getting(JacocoReport::class)
    test {
        useJUnitPlatform()
        dependsOn(checkstyleMain, checkstyleTest)
        dependsOn(licenseMain, licenseTest)
        finalizedBy(jacocoTestReport)
    }

    jacocoTestReport {
        dependsOn(test)
        reports {
            xml.isEnabled = true
            html.isEnabled = false
        }
    }

    javadoc {
        val opt = options as StandardJavadocDocletOptions
        opt.addStringOption("Xdoclint:none", "-quiet")

        opt.encoding("UTF-8")
        opt.charSet("UTF-8")
        opt.source("8")
        doFirst {
            opt.links(
                "https://docs.oracle.com/javase/8/docs/api/"
            )
        }
    }
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = sourceCompatibility
    disableAutoTargetJvm()
}

license {
    header = rootProject.file("LICENCE-HEADER")
    ext["year"] = Calendar.getInstance().get(Calendar.YEAR)
    include("**/*.java")

    mapping("java", "DOUBLESLASH_STYLE")
}

checkstyle {
    toolVersion = "8.41.1"
    val configRoot = rootProject.projectDir
    configDirectory.set(configRoot)
    configProperties["basedir"] = configRoot.absolutePath
}

jacoco {
    reportsDirectory.set(rootProject.buildDir.resolve("reports").resolve("jacoco"))
}

java {
    withSourcesJar()
    withJavadocJar()
}

tasks {
    compileJava {
        options.compilerArgs.add("-parameters")
    }

    compileTestJava {
        options.compilerArgs.add("-parameters")
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }

    repositories {
        maven {
            name = "proxi-nexus"
            val repo = if (project.version.toString().endsWith("-SNAPSHOT")) {
                "snapshots"
            } else {
                "releases"
            }
            url = uri("https://nexus.proximyst.com/repository/maven-$repo/")
            credentials {
                val proxiUser: String? by project
                val proxiPassword: String? by project
                username = proxiUser
                password = proxiPassword
            }
        }
    }
}