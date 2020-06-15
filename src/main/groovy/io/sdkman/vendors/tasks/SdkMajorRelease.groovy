package io.sdkman.vendors.tasks

class SdkMajorRelease extends SdkmanVendorBaseTask {

    static final ANNOUNCE_ENDPOINT = "/announce/struct"

    static final RELEASE_ENDPOINT = "/release"

    static final DEFAULT_ENDPOINT = "/default"

    String downloadUrl
    String hashtag

    SdkMajorRelease() {
        description = "Convenience task performs a Major Release consisting of Release, Default and Announce combined on SDKMAN!"
    }

    @Override
    void executeTask() {
        execRelease(apiUrl, RELEASE_ENDPOINT, candidate, version, platform, downloadUrl, consumerKey, consumerToken)
        execAnnounce(apiUrl, ANNOUNCE_ENDPOINT, candidate, version, hashtag, consumerKey, consumerToken)
        execDefault(apiUrl, DEFAULT_ENDPOINT, candidate, version, consumerKey, consumerToken)
    }
}