package io.sdkman.vendors.tasks

class SdkDefaultVersionTask extends SdkmanVendorBaseTask {

    SdkDefaultVersionTask() {
        description = "Make an existing Candidate Version the new Default on SDKMAN!"
    }

    @Override
    void executeTask() {
        execDefault(apiUrl, candidate, version, consumerKey, consumerToken)
    }
}
