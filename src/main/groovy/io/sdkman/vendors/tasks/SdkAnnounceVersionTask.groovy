package io.sdkman.vendors.tasks

class SdkAnnounceVersionTask extends SdkmanVendorBaseTask {

    static final String ANNOUNCE_ENDPOINT = "/announce/struct"

    String hashtag

    SdkAnnounceVersionTask() {
        description = "Announce a Release on SDKMAN!"
    }

    @Override
    void executeTask() {
        execAnnounce(apiUrl, ANNOUNCE_ENDPOINT, candidate, version, hashtag, consumerKey, consumerToken)
    }
}
