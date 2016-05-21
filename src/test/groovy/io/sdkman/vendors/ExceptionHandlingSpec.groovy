package io.sdkman.vendors

import org.gradle.api.logging.Logger
import spock.lang.Specification
import wslite.http.HTTPResponse
import wslite.http.HTTPRequest
import wslite.rest.RESTClientException

class ExceptionHandlingSpec extends Specification {

    private static class UnderTest implements ExceptionHandling {}

    def underTest = new UnderTest()

    def request = Mock(HTTPRequest)
    def response = Mock(HTTPResponse)

    def log = Mock(Logger)

    void "should successfully invoke function"() {
        expect:
        assert underTest.withTry(log, { true })
    }

    void "should extract status and json message on rest client exception"() {
        when:
        underTest.withTry(log, { throw new RESTClientException("boom!", request, response) })

        then:
        1 * response.getContentAsString() >> '{"message": "pow!"}'
        1 * response.getStatusCode() >> 400
        1 * log.error("Error: 400: pow!")
    }

    void "should extract status and raw message if invalid json on rest client exception"() {
        when:
        underTest.withTry(log, { throw new RESTClientException("boom!", request, response) })

        then:
        1 * response.getContentAsString() >> '<html>kapow</html>'
        1 * response.getStatusCode() >> 400
        1 * log.error("Error: 400: <html>kapow</html>")
    }

}
