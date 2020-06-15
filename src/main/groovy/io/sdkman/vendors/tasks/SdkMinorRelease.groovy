package io.sdkman.vendors.tasks

class SdkMinorRelease extends SdkmanVendorBaseTask {

    String downloadUrl
    String hashtag

    SdkMinorRelease() {
        description = "Convenience task performs a Minor Release consisting of Release and Announce combined on SDKMAN!"
    }

    @Override
    void executeTask() {
        execRelease(apiUrl, candidate, version, platform, downloadUrl, consumerKey, consumerToken)
        execAnnounce(apiUrl, candidate, version, hashtag, consumerKey, consumerToken)
    }
}
