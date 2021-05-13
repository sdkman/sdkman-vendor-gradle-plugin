package io.sdkman.vendors


import static com.github.tomakehurst.wiremock.client.WireMock.*
import static io.sdkman.vendors.infra.ApiEndpoints.*
import static io.sdkman.vendors.stubs.Stubs.verifyPost
import static io.sdkman.vendors.stubs.Stubs.verifyPut
import static org.gradle.testkit.runner.TaskOutcome.FAILED
import static org.gradle.testkit.runner.TaskOutcome.SUCCESS

class SdkConvenienceTaskSpec extends AbstractIntegrationSpec {
    def "should perform a minor release with structured announcement"() {
        given:
        buildFile << """
            sdkman {
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
        succeeds('sdkMinorRelease')

        then:
        result.output.contains('Releasing grails x.y.z for UNIVERSAL...')
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
        buildFile << """
            sdkman {
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
        succeeds('sdkMajorRelease')

        then:
        result.output.contains('Releasing grails x.y.z for UNIVERSAL...')
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

    def "should fail major release gracefully for any non-2xx error received from the API"() {
        given:
        buildFile << """
            sdkman {
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
                .willReturn(aResponse().withStatus(500)))
        stubFor(put(urlEqualTo(DEFAULT_ENDPOINT))
                .willReturn(okJson("""{"status": 202, "message":"success"}""")))

        when:
        fails('sdkMajorRelease')

        then:
        result.output.contains('Releasing grails x.y.z for UNIVERSAL...')
        result.output.contains('Announcing for grails x.y.z...')
        result.output.contains('Response: 500 Server Error')
        result.task(":sdkAnnounceVersion").outcome == FAILED
    }

    def "should fail minor release gracefully for any non-2xx error received from the API"() {
        given:
        buildFile << """
            sdkman {
                candidate = "grails"
                version = "x.y.z"
                url = "https://host/grails-x.y.z.zip"
                hashtag = "grailsfw"
            }
        """

        and:
        stubFor(post(urlEqualTo(RELEASE_ENDPOINT))
                .willReturn(aResponse().withStatus(500)))
        stubFor(post(urlEqualTo(ANNOUNCE_ENDPOINT))
                .willReturn(aResponse().withStatus(500)))

        when:
        fails('sdkMinorRelease')

        then:
        result.output.contains('Releasing grails x.y.z for UNIVERSAL...')
        result.output.contains('Response: 500 Server Error')
        result.task(":sdkReleaseVersion").outcome == FAILED
    }
}
