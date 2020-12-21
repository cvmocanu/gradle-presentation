import org.asciidoctor.gradle.jvm.AsciidoctorTask

plugins {
    id("org.asciidoctor.jvm.convert")
}

repositories {
    jcenter()
}

val asciidoctorTask = tasks.withType(AsciidoctorTask::class.java) {
    baseDirFollowsSourceFile()
    sources {
        include("index.adoc")
    }
}

asciidoctorj {
    modules {
        diagram.use()
        epub.use()
    }
}

val packageHtmlTask = tasks.register<Tar>("package-html") {
    from(asciidoctorTask)
    archiveBaseName.set("gradle-presentation-html")
    compression = Compression.GZIP
}

tasks.assemble {
    dependsOn(packageHtmlTask)
}
