package io.sdkman.vendors.infra

import groovy.json.JsonSlurper
import org.gradle.api.GradleException
import org.gradle.api.logging.Logger

trait ApiErrorResponseHandler {

    def handleNon2xxApiResponse(fun) {
        ApiResponse response = fun()
        if(!(response.code in 200..299)) {
            throw new GradleException("Response: ${response.code} ${response.message}")
        }
    }
}
