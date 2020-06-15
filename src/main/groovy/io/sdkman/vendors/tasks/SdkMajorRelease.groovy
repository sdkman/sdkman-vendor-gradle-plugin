package io.sdkman.vendors.tasks

class SdkMajorRelease extends SdkMinorRelease {

    static final DEFAULT_ENDPOINT = "/default"

    SdkMajorRelease() {
        description = "Convenience task performs a Major Release consisting of Release, Default and Announce combined on SDKMAN!"
    }

    @Override
    void executeTask() {
        super.executeTask()
        execDefault(apiUrl, DEFAULT_ENDPOINT, candidate, version, consumerKey, consumerToken)
    }
}