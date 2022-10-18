import org.asciidoctor.gradle.jvm.AbstractAsciidoctorTask
import org.asciidoctor.gradle.jvm.AsciidoctorTask
import org.asciidoctor.gradle.jvm.pdf.AsciidoctorPdfTask

plugins {
    id("org.asciidoctor.jvm.convert")
    id("org.asciidoctor.jvm.pdf")
}

repositories {
    mavenCentral()
}

configureAsciiDoctor()
configurePackage()

//-------------------- functions --------------------
fun Project.configureAsciiDoctor() {
    asciidoctorj {
        modules {
            diagram.use()
            pdf.use()
        }
    }

    tasks.withType(AbstractAsciidoctorTask::class.java) {
        baseDirFollowsSourceFile()
        sources {
            include("gradle-presentation.adoc")
        }
    }

}

fun Project.configurePackage() {
    configureHtmlPackage()
    configurePdfPackage()
}

fun Project.configureHtmlPackage() {
    val packageHtmlTask = tasks.register<Tar>("package-html") {
        from(
            tasks.withType(AsciidoctorTask::class.java)
        )
        archiveBaseName.set("gradle-presentation-html")
        compression = Compression.GZIP

        exclude(".asciidoctor")
    }
    tasks.assemble {
        dependsOn(packageHtmlTask)
    }
}

fun Project.configurePdfPackage() {
    val packagePdfTask = tasks.register<Tar>("package-pdf") {
        from(
            tasks.withType(AsciidoctorPdfTask::class.java)
        )
        archiveBaseName.set("gradle-presentation-pdf")
        compression = Compression.GZIP

        include("*.pdf")
    }
    tasks.assemble {
        dependsOn(packagePdfTask)
    }
}
