package net.gvmtool.vendors

import org.gradle.api.Project
import spock.lang.Specification

class GvmVendorPluginSpec extends Specification {

    Project project = Mock()
    GvmVendorPlugin plugin

    void setup(){
        plugin = new GvmVendorPlugin()
    }

    void "should register a gvmReleaseVersion task"() {
        when:
        plugin.apply(project)

        then:
        1 * project.task('gvmReleaseVersion', type: GvmReleaseVersionTask)
    }

    void "should register a gvmDefaultVersion task"() {
        when:
        plugin.apply(project)

        then:
        1 * project.task('gvmDefaultVersion', type: GvmDefaultVersionTask)
    }

}
