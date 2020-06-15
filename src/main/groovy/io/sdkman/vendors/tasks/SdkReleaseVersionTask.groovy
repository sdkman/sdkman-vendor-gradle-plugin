package io.sdkman.vendors.tasks

class SdkReleaseVersionTask extends SdkmanVendorBaseTask {

    static final RELEASE_ENDPOINT = "/release"

    String downloadUrl

    SdkReleaseVersionTask() {
        description = "Release a new Candidate Version on SDKMAN!"
    }

    @Override
    void executeTask() {
        execRelease(apiUrl, RELEASE_ENDPOINT, candidate, version, platform, downloadUrl, consumerKey, consumerToken)
    }
}
