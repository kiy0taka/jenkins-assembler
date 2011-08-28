package jenkins.assembler

import groovy.beans.Bindable

class JenkinsAssemblerModel {
    String coreUrl
    @Bindable String status
    @Bindable List plugins
}