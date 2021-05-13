package io.sdkman.vendors


import static com.github.tomakehurst.wiremock.client.WireMock.*
import static io.sdkman.vendors.infra.ApiEndpoints.RELEASE_ENDPOINT
import static io.sdkman.vendors.stubs.Stubs.verifyPost
import static org.gradle.testkit.runner.TaskOutcome.FAILED
import static org.gradle.testkit.runner.TaskOutcome.SUCCESS

class SdkReleaseVersionSpec extends AbstractIntegrationSpec {
    def "should perform a single UNIVERSAL release"() {
        given:
        buildFile << """
            sdkman {
                candidate = "grails"
                version = "x.y.z"
                url = "https://host/grails-x.y.z.zip"
            }
        """

        and:
        stubFor(post(urlEqualTo(RELEASE_ENDPOINT))
                .willReturn(okJson("""{"status": 201, "message":"success"}""")))

        when:
        succeeds('sdkReleaseVersion')

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
        buildFile << """
            sdkman {
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
        succeeds('sdkReleaseVersion')

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
        buildFile << """
            sdkman {
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
        succeeds('sdkReleaseVersion')

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

    def "should fail gracefully for any non-2xx error received from the API"() {
        given:
        buildFile << """
            sdkman {
                candidate = "grails"
                version = "x.y.z"
                url = "https://host/grails-x.y.z.zip"
            }
        """

        and:
        stubFor(post(urlEqualTo(RELEASE_ENDPOINT))
                .willReturn(aResponse().withStatus(500)))

        when:
        fails('sdkReleaseVersion')
        then:
        result.output.contains('Releasing grails x.y.z for UNIVERSAL...')
        result.output.contains('Response: 500 Server Error')
        result.task(":sdkReleaseVersion").outcome == FAILED
    }
}
