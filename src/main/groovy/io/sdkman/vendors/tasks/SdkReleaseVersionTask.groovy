package io.sdkman.vendors.tasks

import io.sdkman.vendors.infra.ApiResponse
import org.gradle.api.tasks.Input

class SdkReleaseVersionTask extends SdkmanVendorBaseTask {

    @Input
    Map<String, String> platforms

    SdkReleaseVersionTask() {
        description = "Release a new Candidate Version on SDKMAN!"
    }

    @Override
    ApiResponse executeTask() {
        List<ApiResponse> responses = platforms.collect { String platform, String url ->
            execRelease(apiUrl, candidate, version, platform, url, consumerKey, consumerToken)
        }
        responses.find { !(it.code in 200..299) } ?: responses.last()
    }
}
