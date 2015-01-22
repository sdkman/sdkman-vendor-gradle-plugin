package net.gvmtool.vendors.model

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
            apis {
                release {
                    apiUrl = "https://release-dev.cfapps.io"
                    clientId = "client_id"
                    clientSecret = "client_secret"
                    username = "username"
                    password = "password"
                }
            }

            candidate = "grails"
            version = "2.4.4"
            url = "http://dist.springframework.org.s3.amazonaws.com/release/GRAILS/grails-2.4.4.zip"
        }

        GvmExtension config = project.properties.get("gvm")
        def releaseApi = config.apis.asMap.get("release")

        then:
        releaseApi.apiUrl == "https://release-dev.cfapps.io"
        releaseApi.clientId == "client_id"
        releaseApi.clientSecret == "client_secret"
        releaseApi.username == "username"
        releaseApi.password == "password"
        config.candidate == "grails"
        config.version == "2.4.4"
        config.url == "http://dist.springframework.org.s3.amazonaws.com/release/GRAILS/grails-2.4.4.zip"
    }

    void "should load multiple apis config blocks from build config"() {
        when:
        project.apply plugin: "net.gvmtool.vendor"
        project.gvm {
            apis {
                release {
                    apiUrl = "https://release-dev.cfapps.io"
                    clientId = "client_id"
                    clientSecret = "client_secret"
                    username = "username"
                    password = "password"
                }
                broadcast {
                    apiUrl = "https://bcast-dev.cfapps.io"
                    clientId = "client_id"
                    clientSecret = "client_secret"
                    username = "username"
                    password = "password"
                }
            }

            candidate = "grails"
            version = "2.4.4"
            url = "http://dist.springframework.org.s3.amazonaws.com/release/GRAILS/grails-2.4.4.zip"
        }

        GvmExtension config = project.properties.get("gvm")

        then:
        config.apis.size() == 2
        config.apis.asMap.containsKey("release")
        config.apis.asMap.containsKey("broadcast")
    }
}
