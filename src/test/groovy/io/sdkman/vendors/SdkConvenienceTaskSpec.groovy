package io.sdkman.vendors


import com.github.tomakehurst.wiremock.junit.WireMockRule
import org.gradle.testkit.runner.GradleRunner
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification

import static com.github.tomakehurst.wiremock.client.WireMock.*
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options
import static io.sdkman.vendors.infra.ApiEndpoints.*
import static io.sdkman.vendors.stubs.Stubs.verifyPost
import static io.sdkman.vendors.stubs.Stubs.verifyPut
import static org.gradle.testkit.runner.TaskOutcome.SUCCESS

class SdkConvenienceTaskSpec extends Specification {

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

    def "should perform a minor release with structured announcement"() {
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
        stubFor(post(urlEqualTo(RELEASE_ENDPOINT))
                .willReturn(okJson("""{"status": 201, "message":"success"}""")))
        stubFor(post(urlEqualTo(ANNOUNCE_ENDPOINT))
                .willReturn(okJson("""{"status": 201, "message":"success"}""")))

        when:
        def result = GradleRunner.create()
                .withProjectDir(testProjectDir.root)
                .withArguments('sdkMinorRelease')
                .withPluginClasspath()
                .build()

        then:
        result.output.contains('Releasing grails x.y.z...')
        result.output.contains('Announcing for grails x.y.z...')
        result.task(":sdkMinorRelease").outcome == SUCCESS
        verifyPost(RELEASE_ENDPOINT,
                """
                    {
                        "candidate":"grails",
                        "version":"x.y.z",
                        "platform":"UNIVERSAL",
                        "url":"https://host/grails-x.y.z.zip"
                    }
                """)
        verifyPost(ANNOUNCE_ENDPOINT,
                """
                    {
                        "candidate": "grails", 
                        "version": "x.y.z", 
                        "hashtag": "grailsfw"
                    }
                """)
    }

    def "should perform a major release with structured announcement"() {
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
        stubFor(post(urlEqualTo(RELEASE_ENDPOINT))
                .willReturn(okJson("""{"status": 201, "message":"success"}""")))
        stubFor(post(urlEqualTo(ANNOUNCE_ENDPOINT))
                .willReturn(okJson("""{"status": 201, "message":"success"}""")))
        stubFor(put(urlEqualTo(DEFAULT_ENDPOINT))
                .willReturn(okJson("""{"status": 202, "message":"success"}""")))

        when:
        def result = GradleRunner.create()
                .withProjectDir(testProjectDir.root)
                .withArguments('sdkMajorRelease')
                .withPluginClasspath()
                .build()

        then:
        result.output.contains('Releasing grails x.y.z...')
        result.output.contains('Announcing for grails x.y.z...')
        result.output.contains('Releasing grails x.y.z as candidate default...')
        result.task(":sdkMajorRelease").outcome == SUCCESS
        verifyPost(RELEASE_ENDPOINT,
                """
                    {
                        "candidate":"grails",
                        "version":"x.y.z",
                        "platform":"UNIVERSAL",
                        "url":"https://host/grails-x.y.z.zip"
                    }
                 """)
        verifyPost(ANNOUNCE_ENDPOINT,
                """
                    {
                        "candidate": "grails", 
                        "version": "x.y.z", 
                        "hashtag": "grailsfw"
                    }
                 """)
        verifyPut(DEFAULT_ENDPOINT,
                """
                    {
                        "candidate": "grails", 
                        "version": "x.y.z" 
                    }
                """)
    }
}
