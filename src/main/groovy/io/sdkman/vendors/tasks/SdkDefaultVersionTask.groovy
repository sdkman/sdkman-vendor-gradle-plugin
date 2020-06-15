package io.sdkman.vendors.tasks

import io.sdkman.vendors.tasks.SdkmanVendorBaseTask

class SdkDefaultVersionTask extends SdkmanVendorBaseTask {

    static final DEFAULT_ENDPOINT = "/default"

    SdkDefaultVersionTask() {
        description = "Make an existing Candidate Version the new Default on SDKMAN!"
    }

    @Override
    void executeTask() {
        execDefault(apiUrl, DEFAULT_ENDPOINT, candidate, version, consumerKey, consumerToken)
    }
}
