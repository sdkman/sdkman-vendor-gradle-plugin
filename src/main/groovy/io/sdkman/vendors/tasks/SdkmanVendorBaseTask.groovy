package io.sdkman.vendors.tasks

import io.sdkman.vendors.infra.ApiExecutions
import io.sdkman.vendors.infra.ApiErrorResponseHandler
import io.sdkman.vendors.infra.ApiResponse
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

abstract class SdkmanVendorBaseTask extends DefaultTask
        implements ApiErrorResponseHandler, ApiExecutions {

    @Input
    String apiUrl

    @Input
    String candidate

    @Input
    String version

    @Input
    Map<String, String> platforms

    @Input
    String consumerKey

    @Input
    String consumerToken

    @TaskAction
    void start() {
        handleNon2xxApiResponse {
            executeTask()
        }
    }

    abstract ApiResponse executeTask()

}
