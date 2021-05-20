package io.sdkman.vendors.tasks

import groovy.transform.CompileStatic
import io.sdkman.vendors.infra.ApiErrorResponseHandler
import io.sdkman.vendors.infra.ApiExecutions
import org.gradle.api.tasks.TaskAction

@CompileStatic
abstract class SdkDefaultVersion extends SdkmanVendorBaseTask implements ApiErrorResponseHandler, ApiExecutions {
    @TaskAction
    void start() {
        handleNon2xxApiResponse {
            execDefault(apiUrl.get(), candidate.get(), version.get(), consumerKey.get(), consumerToken.get())
        }
    }
}
