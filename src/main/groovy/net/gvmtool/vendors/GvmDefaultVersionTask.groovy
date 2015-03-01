package net.gvmtool.vendors

import net.gvmtool.vendors.model.GvmExtension

class GvmDefaultVersionTask extends GvmVendorBaseTask {

    static final DEFAULT_ENDPOINT = "/default"

    GvmDefaultVersionTask() {
        description = "Make an existing Candidate Version the new Default on GVM."
    }

    @Override
    void execute(GvmExtension config) {
        withConnection(config.apiUrl) {
            logger.quiet("Releasing $config.candidate $config.version...")

            def values = [candidate: config.candidate, version: config.version]

            def response = put(restClient, DEFAULT_ENDPOINT,
                    config.consumerKey, config.consumerToken, values)

            logger.quiet("Response: ${response.statusCode}: ${response.contentAsString}...")
        }
    }
}
