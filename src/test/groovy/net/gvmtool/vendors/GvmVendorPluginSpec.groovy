package net.gvmtool.vendors

import org.gradle.api.Project
import spock.lang.Specification

class GvmVendorPluginSpec extends Specification {


    GvmVendorPlugin plugin

    void setup(){
        plugin = new GvmVendorPlugin()
    }

    void "should register a gvmPublish task"() {
        given:
        Project project = Mock()

        when:
        plugin.apply(project)

        then:
        1 * project.task('gvmPublish', type: GvmPublishTask)
    }

}
