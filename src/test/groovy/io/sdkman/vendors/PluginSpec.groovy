package io.sdkman.vendors

import io.sdkman.vendors.tasks.SdkAnnounceVersionTask
import io.sdkman.vendors.tasks.SdkDefaultVersionTask
import io.sdkman.vendors.tasks.SdkMajorRelease
import io.sdkman.vendors.tasks.SdkMinorRelease
import io.sdkman.vendors.tasks.SdkReleaseVersionTask
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
        project.tasks.sdkReleaseVersion instanceof SdkReleaseVersionTask
        project.tasks.sdkAnnounceVersion instanceof SdkAnnounceVersionTask
        project.tasks.sdkDefaultVersion instanceof SdkDefaultVersionTask
        project.tasks.sdkMajorRelease instanceof SdkMajorRelease
        project.tasks.sdkMinorRelease instanceof SdkMinorRelease
    }

    def "should add extension"() {

        expect:
        project.extensions.sdkman
        project.extensions.sdkman.api == "https://vendors.sdkman.io"
    }
}
