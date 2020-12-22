rootProject.name = "gradle-presentation"

pluginManagement {
    plugins {
        val versions = object {
            val asciidoctor = "3.3.0"
        }

        id("org.asciidoctor.jvm.convert") version versions.asciidoctor
        id("org.asciidoctor.jvm.pdf") version versions.asciidoctor
    }
}
