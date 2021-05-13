package io.sdkman.vendors

import io.sdkman.vendors.tasks.SdkAnnounceVersion
import io.sdkman.vendors.tasks.SdkDefaultVersion

import io.sdkman.vendors.tasks.SdkReleaseVersion
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification


class PluginSpec extends Specification{

    public static final String PLUGIN_ID = 'io.sdkman.vendors'

    Project project

    def setup() {
        project = ProjectBuilder.builder().build()
        project.apply plugin: PLUGIN_ID
    }

    def "should add tasks"() {

        expect:
        project.tasks.sdkReleaseVersion instanceof SdkReleaseVersion
        project.tasks.sdkAnnounceVersion instanceof SdkAnnounceVersion
        project.tasks.sdkDefaultVersion instanceof SdkDefaultVersion
    }

    def "should add extension"() {
        expect:
        project.extensions.sdkman
        project.extensions.sdkman.apiUrl.get() == "https://vendors.sdkman.io"
    }
}
