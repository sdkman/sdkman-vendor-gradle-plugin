package io.sdkman.vendors

import static com.github.tomakehurst.wiremock.client.WireMock.*
import static io.sdkman.vendors.infra.ApiEndpoints.getRELEASE_ENDPOINT
import static io.sdkman.vendors.stubs.Stubs.verifyPost
import static org.gradle.testkit.runner.TaskOutcome.SUCCESS

class KotlinDslSupportSpec extends AbstractIntegrationSpec {
    @Override
    protected Dsl getDsl() {
        return Dsl.KOTLIN
    }

    def "users can configure the plugin via Kotlin DSL"() {
        given:
        buildFile << """
            sdkman {
                candidate.set("grails")
                version.set("x.y.z")
                url.set("https://host/grails-x.y.z.zip")
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
}
