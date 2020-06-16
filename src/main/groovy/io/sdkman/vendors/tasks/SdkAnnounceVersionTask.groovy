package io.sdkman.vendors.tasks

import io.sdkman.vendors.infra.ApiResponse

class SdkAnnounceVersionTask extends SdkmanVendorBaseTask {

    String hashtag

    SdkAnnounceVersionTask() {
        description = "Announce a Release on SDKMAN!"
    }

    @Override
    ApiResponse executeTask() {
        execAnnounce(apiUrl, candidate, version, hashtag, consumerKey, consumerToken)
    }
}
