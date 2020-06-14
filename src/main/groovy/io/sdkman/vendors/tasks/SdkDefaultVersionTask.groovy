package io.sdkman.vendors.tasks

import io.sdkman.vendors.tasks.SdkmanVendorBaseTask

class SdkDefaultVersionTask extends SdkmanVendorBaseTask {

    static final DEFAULT_ENDPOINT = "/default"

    SdkDefaultVersionTask() {
        description = "Make an existing Candidate Version the new Default on SDKMAN!"
    }

    @Override
    void executeTask() {
        withConnection(apiUrl, DEFAULT_ENDPOINT, consumerKey, consumerToken) { conn ->
            logger.quiet("Releasing $candidate $version as candidate default...")
            def values = [candidate: candidate, version: version]
            def response = put(conn, values)
            logger.quiet("Response: ${response.responseCode}: ${response.responseMessage}...")
        }
    }
}
