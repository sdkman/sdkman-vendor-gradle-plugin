package io.sdkman.vendors.stubs

import com.github.tomakehurst.wiremock.matching.RequestPatternBuilder

import static com.github.tomakehurst.wiremock.client.WireMock.*

class Stubs {

    static verifyPost(String endpoint, String json) {
        verifyRequest(postRequestedFor(urlEqualTo(endpoint)), json)
    }

    static verifyPut(String endpoint, String json) {
        verifyRequest(putRequestedFor(urlEqualTo(endpoint)), json)
    }

    private static boolean verifyRequest(RequestPatternBuilder builder, String json) {
        return verify(builder
                .withHeader("Content-Type", equalTo("application/json"))
                .withHeader("Accepts", equalTo("application/json"))
                .withHeader("Consumer-Key", equalTo("SOME_KEY"))
                .withHeader("Consumer-Token", equalTo("SOME_TOKEN"))
                .withRequestBody(equalToJson(json))) ?: true
    }
}
