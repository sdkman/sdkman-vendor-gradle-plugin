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
            def credentials = credentials.get()
            execDefault(apiUrl.get(), candidate.get(), version.get(), credentials.username, credentials.password)
        }
    }
}
