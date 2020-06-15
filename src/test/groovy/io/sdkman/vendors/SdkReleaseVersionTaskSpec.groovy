package io.sdkman.vendors

import com.github.tomakehurst.wiremock.junit.WireMockRule
import org.gradle.testkit.runner.GradleRunner
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification

import static com.github.tomakehurst.wiremock.client.WireMock.*
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options
import static io.sdkman.vendors.infra.ApiEndpoints.RELEASE_ENDPOINT
import static io.sdkman.vendors.stubs.Stubs.verifyPost
import static org.gradle.testkit.runner.TaskOutcome.SUCCESS

class SdkReleaseVersionTaskSpec extends Specification {

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

    def "should perform a single UNIVERSAL release"() {
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
        }
    """

        and:
        stubFor(post(urlEqualTo(RELEASE_ENDPOINT))
                .willReturn(okJson("""{"status": 201, "message":"success"}""")))

        when:
        def result = GradleRunner.create()
                .withProjectDir(testProjectDir.root)
                .withArguments('sdkReleaseVersion')
                .withPluginClasspath()
                .build()

        then:
        result.output.contains('Releasing grails x.y.z for UNIVERSAL...')
        result.task(":sdkReleaseVersion").outcome == SUCCESS
        verifyPost(RELEASE_ENDPOINT,
                """
                    {
                        "candidate":"grails",
                        "version":"x.y.z",
                        "platform":"UNIVERSAL",
                        "url":"https://host/grails-x.y.z.zip"
                    }
                """)
    }

    def "should perform a single PLATFORM SPECIFIC release"() {
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
            candidate = "micronaut"
            version = "x.y.z"
            platforms = [
                "MAC_OSX":"https://host/micronaut-x.y.z-macosx.zip" 
            ]
        }
    """

        and:
        stubFor(post(urlEqualTo(RELEASE_ENDPOINT))
                .willReturn(okJson("""{"status": 201, "message":"success"}""")))

        when:
        def result = GradleRunner.create()
                .withProjectDir(testProjectDir.root)
                .withArguments('sdkReleaseVersion')
                .withPluginClasspath()
                .build()

        then:
        result.output.contains('Releasing micronaut x.y.z for MAC_OSX...')
        result.task(":sdkReleaseVersion").outcome == SUCCESS
        verifyPost(RELEASE_ENDPOINT,
                """
                    {
                        "candidate":"micronaut",
                        "version":"x.y.z",
                        "url":"https://host/micronaut-x.y.z-macosx.zip",
                        "platform":"MAC_OSX"
                    }
                """)
    }

    def "should perform a multi PLATFORM SPECIFIC release"() {
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
            candidate = "micronaut"
            version = "x.y.z"
            platforms = [
                "MAC_OSX":"https://host/micronaut-x.y.z-macosx.zip",
                "WINDOWS_64":"https://host/micronaut-x.y.z-win.zip", 
                "LINUX_64":"https://host/micronaut-x.y.z-linux64.zip", 
            ]
        }
    """

        and:
        stubFor(post(urlEqualTo(RELEASE_ENDPOINT))
                .willReturn(okJson("""{"status": 201, "message":"success"}""")))

        when:
        def result = GradleRunner.create()
                .withProjectDir(testProjectDir.root)
                .withArguments('sdkReleaseVersion')
                .withPluginClasspath()
                .build()

        then:
        result.output.contains('Releasing micronaut x.y.z for MAC_OSX...')
        result.output.contains('Releasing micronaut x.y.z for WINDOWS_64...')
        result.output.contains('Releasing micronaut x.y.z for LINUX_64...')
        result.task(":sdkReleaseVersion").outcome == SUCCESS
        verifyPost(RELEASE_ENDPOINT,
                """
                    {
                        "candidate":"micronaut",
                        "version":"x.y.z",
                        "url":"https://host/micronaut-x.y.z-macosx.zip",
                        "platform":"MAC_OSX"
                    }
                """)
        verifyPost(RELEASE_ENDPOINT,
                """
                    {
                        "candidate":"micronaut",
                        "version":"x.y.z",
                        "url":"https://host/micronaut-x.y.z-win.zip",
                        "platform":"WINDOWS_64"
                    }
                """)
        verifyPost(RELEASE_ENDPOINT,
                """
                    {
                        "candidate":"micronaut",
                        "version":"x.y.z",
                        "url":"https://host/micronaut-x.y.z-linux64.zip",
                        "platform":"LINUX_64"
                    }
                """)
    }
}
