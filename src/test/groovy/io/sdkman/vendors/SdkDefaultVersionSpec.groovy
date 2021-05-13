package io.sdkman.vendors


import static com.github.tomakehurst.wiremock.client.WireMock.*
import static io.sdkman.vendors.infra.ApiEndpoints.DEFAULT_ENDPOINT
import static io.sdkman.vendors.stubs.Stubs.verifyPut
import static org.gradle.testkit.runner.TaskOutcome.FAILED
import static org.gradle.testkit.runner.TaskOutcome.SUCCESS

class SdkDefaultVersionSpec extends AbstractIntegrationSpec {
    def "should set a specific version as candidate default"() {
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
        stubFor(put(urlEqualTo(DEFAULT_ENDPOINT))
                .willReturn(okJson("""{"status": 202, "message":"success"}""")))

        when:
        succeeds('sdkDefaultVersion')

        then:
        result.output.contains('Releasing grails x.y.z as candidate default...')
        result.task(":sdkDefaultVersion").outcome == SUCCESS
        verifyPut(DEFAULT_ENDPOINT,
                """
                    {
                        "candidate": "grails", 
                        "version": "x.y.z" 
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
        stubFor(put(urlEqualTo(DEFAULT_ENDPOINT))
                .willReturn(aResponse().withStatus(400)))

        when:
        fails('sdkDefaultVersion')

        then:
        result.output.contains('Releasing grails x.y.z as candidate default...')
        result.output.contains('Response: 400 Bad Request')
        result.task(":sdkDefaultVersion").outcome == FAILED
    }
}
