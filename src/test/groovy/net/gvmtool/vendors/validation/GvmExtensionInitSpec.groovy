package net.gvmtool.vendors.validation

import net.gvmtool.vendors.GvmExtension
import net.gvmtool.vendors.GvmVendorPlugin
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

class GvmExtensionInitSpec extends Specification {

    Project project

    void setup(){
        project = ProjectBuilder.builder().build()
    }

    void "should apply plugin to project"() {
        when:
        project.apply plugin: "net.gvmtool.vendor"

        then:
        project.plugins.hasPlugin(GvmVendorPlugin)
    }

    void "should load a basic configuration from build config"() {
        when:
        project.apply plugin: "net.gvmtool.vendor"
        project.gvm {
            apiUrl = "https://release-dev.cfapps.io"
            clientId = "some_client_id"
            clientSecret = "some_client_secret"
            username = "some_username"
            password = "some_password"
            candidate = "grails"
            version = "2.4.4"
            url = "http://dist.springframework.org.s3.amazonaws.com/release/GRAILS/grails-2.4.4.zip"
        }
        GvmExtension config = project.properties.get("gvm")

        then:
        config.apiUrl == "https://release-dev.cfapps.io"
        config.clientId == "some_client_id"
        config.clientSecret == "some_client_secret"
        config.username == "some_username"
        config.password == "some_password"
        config.candidate == "grails"
        config.version == "2.4.4"
        config.url == "http://dist.springframework.org.s3.amazonaws.com/release/GRAILS/grails-2.4.4.zip"
    }



}
