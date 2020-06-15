package io.sdkman.vendors.tasks

class SdkReleaseVersionTask extends SdkmanVendorBaseTask {

    Map<String, String> platforms

    SdkReleaseVersionTask() {
        description = "Release a new Candidate Version on SDKMAN!"
    }

    @Override
    void executeTask() {
        platforms.each { String platform, String url ->
            execRelease(apiUrl, candidate, version, platform, url, consumerKey, consumerToken)
        }
    }
}
