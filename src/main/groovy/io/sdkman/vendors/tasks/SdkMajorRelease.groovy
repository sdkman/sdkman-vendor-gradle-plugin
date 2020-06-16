package io.sdkman.vendors.tasks

import io.sdkman.vendors.infra.ApiResponse
import org.gradle.api.tasks.Input

class SdkMajorRelease extends SdkmanVendorBaseTask {

    @Input
    Map<String, String> platforms

    @Input
    String hashtag

    SdkMajorRelease() {
        description = "Convenience task performs a Major Release consisting of Release, Default and Announce combined on SDKMAN!"
    }

    @Override
    ApiResponse executeTask() {
        List<ApiResponse> responses = platforms.collect { String platform, String url ->
            execRelease(apiUrl, candidate, version, platform, url, consumerKey, consumerToken)
        } + execAnnounce(apiUrl, candidate, version, hashtag, consumerKey, consumerToken) +
                execDefault(apiUrl, candidate, version, consumerKey, consumerToken)

        responses.find { !(it.code in 200..299) } ?: responses.last()
    }
}