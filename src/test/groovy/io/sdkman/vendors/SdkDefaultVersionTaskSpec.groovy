package io.sdkman.vendors


import com.github.tomakehurst.wiremock.junit.WireMockRule
import org.gradle.testkit.runner.GradleRunner
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification

import static com.github.tomakehurst.wiremock.client.WireMock.*
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options
import static org.gradle.testkit.runner.TaskOutcome.SUCCESS

class SdkDefaultVersionTaskSpec extends Specification {

    @Rule
    WireMockRule api = new WireMockRule(options().dynamicPort())

    @Rule
    TemporaryFolder testProjectDir = new TemporaryFolder()

    File settingsFile
    File buildFile

    def setup() {
        settingsFile = testProjectDir.newFile('settings.gradle')
        buildFile = testProjectDir.newFile('build.gradle')
    }

    def "should set a specific version as candidate default"() {
        given:
        def baseUrl = api.baseUrl()
        settingsFile << "rootProject.name = 'release-test'"
        buildFile << """
        plugins {
            id 'io.sdkman.vendors'
        }
        sdkman {
            api = "${baseUrl}"
            consumerKey = "SOME_KEY"
            consumerToken = "SOME_TOKEN"
            candidate = "grails"
            version = "x.y.z"
            url = "https://host/grails-x.y.z.zip"
            hashtag = "grailsfw"
        }
    """

        and:
        stubFor(put(urlEqualTo(SdkMajorRelease.DEFAULT_ENDPOINT))
                .willReturn(okJson("""{"status": 202, "message":"success"}""")))

        when:
        def result = GradleRunner.create()
                .withProjectDir(testProjectDir.root)
                .withArguments('sdkDefaultVersion')
                .withPluginClasspath()
                .build()

        then:
        result.output.contains('Releasing grails x.y.z as candidate default...')
        result.task(":sdkDefaultVersion").outcome == SUCCESS
        verify(putRequestedFor(
                urlEqualTo(SdkMajorRelease.DEFAULT_ENDPOINT))
                .withHeader("Content-Type", equalTo("application/json"))
                .withHeader("Accepts", equalTo("application/json"))
                .withHeader("Consumer-Key", equalTo("SOME_KEY"))
                .withHeader("Consumer-Token", equalTo("SOME_TOKEN"))
                .withRequestBody(equalToJson(
            """
                    {
                        "candidate": "grails", 
                        "version": "x.y.z" 
                    }
                  """)
                )
        )
    }
}
