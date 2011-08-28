application {
    title = 'JenkinsAssembler'
    startupGroups = ['jenkins-assembler']

    // Should Griffon exit when no Griffon created frames are showing?
    autoShutdown = true

    // If you want some non-standard application class, apply it here
    //frameClass = 'javax.swing.JFrame'
}
mvcGroups {
    // MVC Group for "jenkins-assembler"
    'jenkins-assembler' {
        model = 'jenkins.assembler.JenkinsAssemblerModel'
        controller = 'jenkins.assembler.JenkinsAssemblerController'
        view = 'jenkins.assembler.JenkinsAssemblerView'
    }

}
