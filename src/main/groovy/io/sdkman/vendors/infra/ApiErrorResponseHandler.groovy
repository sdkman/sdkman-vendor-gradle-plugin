package io.sdkman.vendors.infra

import org.gradle.api.GradleException

trait ApiErrorResponseHandler {

    def handleNon2xxApiResponse(fun) {
        ApiResponse response = fun()
        if(!(response.code in 200..299)) {
            throw new GradleException("Response: ${response.code} ${response.message}")
        }
    }
}
