String template = new File("${project.basedir}/DockerfileTemplate".toString()).getText()
String configuration = new File("${project.basedir}/src/main/resources/config.yml".toString()).getText()

def dockerFileText = new groovy.text.SimpleTemplateEngine().createTemplate(template)
        .make([fileName: project.build.finalName])

println "writing dir " + "${project.basedir}/target/dockerfile"
new File("${project.basedir}/target/dockerfile/".toString()).mkdirs()

println "writing file"
File dockerFile = new File("${project.basedir}/target/dockerfile/Dockerfile".toString())

File configurationFile = new File("${project.basedir}/target/dockerfile/config.yml".toString())

dockerFile.withWriter('UTF-8') { writer ->
    writer.write(dockerFileText)
}

configurationFile.withWriter('UTF-8') { writer ->
    writer.write(configuration)
}


File entryPoint = new File("${project.basedir}/target/dockerfile/entryPoint.sh".toString())
entryPoint.withWriter('UTF-8') { writer ->
    String src = new File("${project.basedir}/src/main/resources/entryPoint.sh".toString()).getText()
    writer.write(src)
}

File sql = new File("${project.basedir}/target/dockerfile/mysql.sql".toString())
sql.withWriter('UTF-8') { writer ->
    String src = new File("${project.basedir}/src/main/resources/mysql.sql".toString()).getText()
    writer.write(src)
}



