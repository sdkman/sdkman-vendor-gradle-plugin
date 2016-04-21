package net.gvmtool.vendors

class SdkDefaultVersionTask extends SdkmanVendorBaseTask {

    static final DEFAULT_ENDPOINT = "/default"

    SdkDefaultVersionTask() {
        description = "Make an existing Candidate Version the new Default on SDKMAN!"
    }

    @Override
    void executeTask() {
        withConnection(apiUrl) {
            logger.quiet("Releasing $candidate $version...")

            def values = [candidate: candidate, version: version]

            def response = put(restClient, DEFAULT_ENDPOINT,
                    consumerKey, consumerToken, values)

            logger.quiet("Response: ${response.statusCode}: ${response.contentAsString}...")
        }
    }
}
