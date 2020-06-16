package io.sdkman.vendors.tasks

import io.sdkman.vendors.infra.ApiResponse
import org.gradle.api.tasks.Input

class SdkAnnounceVersionTask extends SdkmanVendorBaseTask {

    @Input
    String hashtag

    SdkAnnounceVersionTask() {
        description = "Announce a Release on SDKMAN!"
    }

    @Override
    ApiResponse executeTask() {
        execAnnounce(apiUrl, candidate, version, hashtag, consumerKey, consumerToken)
    }
}
