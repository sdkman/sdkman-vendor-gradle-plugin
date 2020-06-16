package io.sdkman.vendors.tasks

import io.sdkman.vendors.infra.ApiResponse

class SdkDefaultVersionTask extends SdkmanVendorBaseTask {

    SdkDefaultVersionTask() {
        description = "Make an existing Candidate Version the new Default on SDKMAN!"
    }

    @Override
    ApiResponse executeTask() {
        execDefault(apiUrl, candidate, version, consumerKey, consumerToken)
    }
}
