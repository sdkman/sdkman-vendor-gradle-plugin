package net.gvmtool.vendors

import net.gvmtool.vendors.model.GvmExtension

class GvmDefaultVersionTask extends GvmVendorBaseTask {

    static final DEFAULT_ENDPOINT = "/default"

    GvmDefaultVersionTask() {
        description = "Make an existing Candidate Version the new Default on GVM."
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
