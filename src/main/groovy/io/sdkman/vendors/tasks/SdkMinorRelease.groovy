package io.sdkman.vendors.tasks

class SdkMinorRelease extends SdkmanVendorBaseTask {

    Map<String, String> platforms
    String hashtag

    SdkMinorRelease() {
        description = "Convenience task performs a Minor Release consisting of Release and Announce combined on SDKMAN!"
    }

    @Override
    void executeTask() {
        platforms.each { String platform, String url ->
            execRelease(apiUrl, candidate, version, platform, url, consumerKey, consumerToken)
        }
        execAnnounce(apiUrl, candidate, version, hashtag, consumerKey, consumerToken)
    }
}
