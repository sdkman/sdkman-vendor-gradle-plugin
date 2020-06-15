package io.sdkman.vendors.tasks

class SdkAnnounceVersionTask extends SdkmanVendorBaseTask {

    String hashtag

    SdkAnnounceVersionTask() {
        description = "Announce a Release on SDKMAN!"
    }

    @Override
    void executeTask() {
        execAnnounce(apiUrl, candidate, version, hashtag, consumerKey, consumerToken)
    }
}
