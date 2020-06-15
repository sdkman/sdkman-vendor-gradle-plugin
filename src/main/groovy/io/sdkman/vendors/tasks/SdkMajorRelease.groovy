package io.sdkman.vendors.tasks

class SdkMajorRelease extends SdkmanVendorBaseTask {

    Map<String, String> platforms
    String hashtag

    SdkMajorRelease() {
        description = "Convenience task performs a Major Release consisting of Release, Default and Announce combined on SDKMAN!"
    }

    @Override
    void executeTask() {
        platforms.each { platform, url ->
            execRelease(apiUrl, candidate, version, platform, url, consumerKey, consumerToken)
        }
        execAnnounce(apiUrl, candidate, version, hashtag, consumerKey, consumerToken)
        execDefault(apiUrl, candidate, version, consumerKey, consumerToken)
    }
}