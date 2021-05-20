package io.sdkman.vendors.tasks

import groovy.transform.CompileStatic
import io.sdkman.vendors.infra.ApiErrorResponseHandler
import io.sdkman.vendors.infra.ApiExecutions
import io.sdkman.vendors.infra.ApiResponse
import org.gradle.api.tasks.TaskAction

@CompileStatic
abstract class SdkReleaseVersion extends SdkmanVendorBaseTask implements ApiErrorResponseHandler, ApiExecutions {
    @TaskAction
    void start() {
        handleNon2xxApiResponse {
            Map<String, String> declaredPlatforms = platforms.get()
            List<ApiResponse> responses = declaredPlatforms.collect { String platform, String url ->
                execRelease(apiUrl.get(), candidate.get(), version.get(), platform, url, consumerKey.get(), consumerToken.get())
            }
            responses.find { !(it.code in 200..299) } ?: responses.last()
        }
    }
}
