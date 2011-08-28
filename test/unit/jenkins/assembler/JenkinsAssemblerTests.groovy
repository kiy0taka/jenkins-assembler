package jenkins.assembler

class JenkinsAssemblerTests extends GroovyTestCase {

    def controller

    void setUp() {
        controller = new JenkinsAssemblerController()
    }

    void test_resolveDependencies() {
        def allPlugins = [
            [name:'foo', install:true, dependencies:[[name:'bar'], [name:'baz']]],
            [name:'bar', install:false, dependencies:[]],
            [name:'baz', install:false, dependencies:[]]
        ]
        controller.resolveDependencies(allPlugins, allPlugins.findAll{ it.install })
        assert allPlugins == [
            [name:'foo', install:true, dependencies:[[name:'bar'], [name:'baz']]],
            [name:'bar', install:true, dependencies:[]],
            [name:'baz', install:true, dependencies:[]]
        ]
    }

    void test_resolveDependencies_ignore_optinal() {
        def allPlugins = [
            [name:'foo', install:true, dependencies:[[name:'bar'], [name:'baz', optional:true]]],
            [name:'bar', install:false, dependencies:[]],
            [name:'baz', install:false, dependencies:[]]
        ]
        controller.resolveDependencies(allPlugins, allPlugins.findAll{ it.install })
        assert allPlugins == [
            [name:'foo', install:true, dependencies:[[name:'bar'], [name:'baz', optional:true]]],
            [name:'bar', install:true, dependencies:[]],
            [name:'baz', install:false, dependencies:[]]
        ]
    }

    void test_resolveDependencies_nodependencies() {
        def allPlugins = [
            [name:'foo', install:true, dependencies:[]],
            [name:'bar', install:false, dependencies:[]]
        ]
        controller.resolveDependencies(allPlugins, allPlugins.findAll{ it.install })
        assert allPlugins == [
            [name:'foo', install:true, dependencies:[]],
            [name:'bar', install:false, dependencies:[]]
        ]
    }

    void test_resolveDependencies_interdependence() {
        def allPlugins = [
            [name:'foo', install:true, dependencies:[[name:'bar']]],
            [name:'bar', install:false, dependencies:[[name:'foo']]]
        ]
        controller.resolveDependencies(allPlugins, allPlugins.findAll{ it.install })
        assert allPlugins == [
            [name:'foo', install:true, dependencies:[[name:'bar']]],
            [name:'bar', install:true, dependencies:[[name:'foo']]]
        ]
    }

    void test_resolveDependencies_cycle_dependencies() {
        def allPlugins = [
            [name:'foo', install:true, dependencies:[[name:'bar']]],
            [name:'bar', install:false, dependencies:[[name:'baz']]],
            [name:'baz', install:false, dependencies:[[name:'foo']]]
        ]
        controller.resolveDependencies(allPlugins, allPlugins.findAll{ it.install })
        assert allPlugins == [
            [name:'foo', install:true, dependencies:[[name:'bar']]],
            [name:'bar', install:true, dependencies:[[name:'baz']]],
            [name:'baz', install:true, dependencies:[[name:'foo']]]
        ]
    }
}
