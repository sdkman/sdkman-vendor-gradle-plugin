package io.sdkman.vendors


import static com.github.tomakehurst.wiremock.client.WireMock.*
import static io.sdkman.vendors.infra.ApiEndpoints.ANNOUNCE_ENDPOINT
import static io.sdkman.vendors.stubs.Stubs.verifyPost
import static org.gradle.testkit.runner.TaskOutcome.FAILED
import static org.gradle.testkit.runner.TaskOutcome.SUCCESS

class SdkAnnounceVersionSpec extends AbstractIntegrationSpec {
    def "should perform a structured announcement"() {
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
        stubFor(post(urlEqualTo(ANNOUNCE_ENDPOINT))
                .willReturn(okJson("""{"status": 202, "message":"success"}""")))

        when:
        succeeds('sdkAnnounceVersion')

        then:
        result.output.contains('Announcing for grails x.y.z...')
        result.task(":sdkAnnounceVersion").outcome == SUCCESS
        verifyPost(ANNOUNCE_ENDPOINT,
                """
                    {
                        "candidate": "grails", 
                        "version": "x.y.z", 
                        "hashtag": "grailsfw"
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
                hashtag = "grailsfw"
            }
        """

        and:
        stubFor(post(urlEqualTo(ANNOUNCE_ENDPOINT))
                .willReturn(aResponse().withStatus(400)))

        when:
        fails('sdkAnnounceVersion')

        then:
        result.output.contains('Announcing for grails x.y.z...')
        result.output.contains('Response: 400 Bad Request')
        result.task(":sdkAnnounceVersion").outcome == FAILED
    }
}
