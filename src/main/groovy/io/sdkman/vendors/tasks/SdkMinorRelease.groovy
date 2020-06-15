package io.sdkman.vendors.tasks

class SdkMinorRelease extends SdkmanVendorBaseTask {

    static final ANNOUNCE_ENDPOINT = "/announce/struct"

    static final RELEASE_ENDPOINT = "/release"

    String downloadUrl
    String hashtag

    SdkMinorRelease() {
        description = "Convenience task performs a Minor Release consisting of Release and Announce combined on SDKMAN!"
    }

    @Override
    void executeTask() {
        execRelease(apiUrl, RELEASE_ENDPOINT, candidate, version, platform, downloadUrl, consumerKey, consumerToken)
        execAnnounce(apiUrl, ANNOUNCE_ENDPOINT, candidate, version, hashtag, consumerKey, consumerToken)
    }
}
