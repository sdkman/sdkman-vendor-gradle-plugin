package io.sdkman.vendors.tasks

class SdkMajorRelease extends SdkmanVendorBaseTask {

    String downloadUrl
    String hashtag

    SdkMajorRelease() {
        description = "Convenience task performs a Major Release consisting of Release, Default and Announce combined on SDKMAN!"
    }

    @Override
    void executeTask() {
        execRelease(apiUrl, candidate, version, platform, downloadUrl, consumerKey, consumerToken)
        execAnnounce(apiUrl, candidate, version, hashtag, consumerKey, consumerToken)
        execDefault(apiUrl, candidate, version, consumerKey, consumerToken)
    }
}