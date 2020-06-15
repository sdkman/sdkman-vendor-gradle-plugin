package io.sdkman.vendors.tasks

class SdkReleaseVersionTask extends SdkmanVendorBaseTask {

    String downloadUrl

    SdkReleaseVersionTask() {
        description = "Release a new Candidate Version on SDKMAN!"
    }

    @Override
    void executeTask() {
        execRelease(apiUrl, candidate, version, platform, downloadUrl, consumerKey, consumerToken)
    }
}
